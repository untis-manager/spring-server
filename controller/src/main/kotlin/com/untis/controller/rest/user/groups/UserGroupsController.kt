package com.untis.controller.rest.user.groups

import com.untis.controller.base.ControllerScope
import com.untis.controller.body.parameter.UserRequestModeParameter
import com.untis.controller.body.response.FullUserResponse
import com.untis.controller.body.response.PartialUserResponse
import com.untis.controller.body.response.course.CourseResponse
import com.untis.controller.body.response.group.GroupResponse
import com.untis.controller.util.ok
import com.untis.controller.util.unauthorized
import com.untis.controller.validating.validateGroupExists
import com.untis.model.Permission
import com.untis.model.User
import com.untis.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * Controller that gives a user access to its own groups
 */
@RestController
@RequestMapping("/user/groups/")
class UserGroupsController @Autowired constructor(

    override val courseService: CourseService,

    override val userService: UserService,

    override val courseTimingService: CourseTimingService,

    override val courseInstanceInfoService: CourseInstanceInfoService,

    override val groupService: GroupService

) : ControllerScope() {

    /**
     * Endpoint that returns the groups of the authenticated user
     *
     * @param user The authenticated user
     * @return The groups
     */
    @GetMapping
    fun getGroups(
        @AuthenticationPrincipal user: User,
    ): List<GroupResponse> = groupService
        .getAllForUser(user.id!!)
        .map(GroupResponse::create)

    /**
     * Endpoint that returns a group specific by id for a user
     *
     * @param user The authenticated user
     * @param id The path variable id of the course
     * @return The group response
     */
    @GetMapping("{id}/")
    fun getGroupById(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User,
    ): GroupResponse {
        validateGroupExists(id, user.id)

        val group = groupService.getById(id)

        return GroupResponse.create(group)
    }

    /**
     * Endpoint that returns the courses of a specific group by the user
     *
     * @param user The authenticated user
     * @param id The path variable id of the course
     * @return The courses
     */
    @GetMapping("{id}/courses/")
    fun getGroupCourses(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User,
    ): List<CourseResponse> {
        validateGroupExists(id, user.id)

        return groupService
            .getCourses(id)
            .map { course ->
                val timings = courseService.getTimings(course.id!!)
                val instanceInfos = courseInstanceInfoService.getByCourse(course.id!!)
                CourseResponse.create(course, timings.toList(), instanceInfos.toList())
            }
    }

    /**
     * Endpoint that returns the members that are part of a specific group by the user
     *
     * If the requested user mode does not conform to the users permission, 401 will happen.
     *
     * @param user The authenticated user
     * @param id The path variable id of the group
     * @param mode The requested mode
     * @return The members
     */
    @Suppress("DuplicatedCode")
    @GetMapping("{id}/users/")
    fun getGroupMembers(
        @PathVariable id: Long,
        @RequestParam mode: UserRequestModeParameter,
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<Any> {
        validateGroupExists(id, user.id)

        val members = groupService.getUsers(id)

        val userPerms = user.role.permissions.users
        val responses = if (userPerms.matches(Permission.Users.Full))
            members.map(FullUserResponse::create)
        else if (userPerms.matches(Permission.Users.Partial))
            members.map(PartialUserResponse::create)
        else null

        return if (responses == null) unauthorized()
        else ok(responses)
    }
}