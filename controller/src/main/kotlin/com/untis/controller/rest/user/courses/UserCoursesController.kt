package com.untis.controller.rest.user.courses

import com.untis.controller.base.ControllerScope
import com.untis.controller.body.parameter.UserRequestModeParameter
import com.untis.controller.body.response.FullUserResponse
import com.untis.controller.body.response.PartialUserResponse
import com.untis.controller.body.response.course.CourseResponse
import com.untis.controller.body.response.group.GroupResponse
import com.untis.controller.util.ok
import com.untis.controller.util.unauthorized
import com.untis.controller.validating.validateCourseExists
import com.untis.model.Permission
import com.untis.model.User
import com.untis.service.CourseInstanceInfoService
import com.untis.service.CourseService
import com.untis.service.CourseTimingService
import com.untis.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * Controller that gives a user access to its own courses
 */
@RestController
@RequestMapping("/user/courses/")
class UserCoursesController @Autowired constructor(

    override val courseService: CourseService,

    override val userService: UserService,

    override val courseTimingService: CourseTimingService,

    override val courseInstanceInfoService: CourseInstanceInfoService

) : ControllerScope() {

    /**
     * Endpoint that returns the courses of the authenticated user
     *
     * @param user The authenticated user
     * @return The course responses
     */
    @GetMapping
    fun getCourses(
        @AuthenticationPrincipal user: User,
    ): List<CourseResponse> = userService
        .getEnrolledCourses(user.id!!)
        .map { course ->
            val timings = courseService.getTimings(course.id!!)
            val instanceInfos = courseInstanceInfoService.getByCourse(course.id!!)
            CourseResponse.create(course, timings.toList(), instanceInfos.toList())
        }

    /**
     * Endpoint that returns a course specific by id for a user
     *
     * @param user The authenticated user
     * @param id The path variable id of the course
     * @return The course response
     */
    @GetMapping("{id}/")
    fun getCourseById(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User,
    ): CourseResponse {
        validateCourseExists(id, user.id)

        val course = courseService.getById(id)
        val timings = courseService.getTimings(course.id!!)
        val instanceInfos = courseInstanceInfoService.getByCourse(course.id!!)

        return CourseResponse.create(course, timings.toList(), instanceInfos.toList())
    }

    /**
     * Endpoint that returns the groups of a specific course by the user
     *
     * @param user The authenticated user
     * @param id The path variable id of the course
     * @return The groups
     */
    @GetMapping("{id}/groups/")
    fun getCourseGroups(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User,
    ): List<GroupResponse> {
        validateCourseExists(id, user.id)

        val groups = courseService.getGroups(id)

        return groups.map(GroupResponse::create)
    }

    /**
     * Endpoint that returns the leaders of a specific course by the user
     *
     * If the requested user mode does not conform to the users permission, 401 will happen.
     *
     * @param user The authenticated user
     * @param id The path variable id of the course
     * @param mode The requested mode
     * @return The leaders
     */
    @Suppress("DuplicatedCode")
    @GetMapping("{id}/leaders/")
    fun getCourseLeaders(
        @PathVariable id: Long,
        @RequestParam mode: UserRequestModeParameter,
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<Any> {
        validateCourseExists(id, user.id)

        val leaders = courseService.getLeaders(id)

        val userPerms = user.role.permissions.users
        val responses = if (userPerms.matches(Permission.Users.Full))
            leaders.map(FullUserResponse::create)
        else if (userPerms.matches(Permission.Users.Partial))
            leaders.map(PartialUserResponse::create)
        else null

        return if (responses == null) unauthorized()
        else ok(responses)
    }
}