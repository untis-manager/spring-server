package com.untis.service.impl

import com.untis.database.entity.GroupEntity
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.UserRepository
import com.untis.model.Course
import com.untis.model.CourseTiming
import com.untis.model.Group
import com.untis.model.User
import com.untis.service.CourseService
import com.untis.service.GroupService
import com.untis.service.mapping.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseCourseService @Autowired constructor(

    val courseRepository: CourseRepository,

    val userRepository: UserRepository,

    val groupService: GroupService,

    val groupRepository: GroupRepository

) : CourseService {


    override fun getAll() = courseRepository
        .findAll()
        .map(::createCourseModel)
        .toSet()


    override fun getById(id: Long) = courseRepository
        .findById(id).get()
        .let(::createCourseModel)

    override fun getByIdAndUser(id: Long, user: Long): Course? {
        val course = courseRepository.findById(id).orElse(null)
        val userEntity = userRepository.findById(user).get()

        val courseGroups = course?.groups?.map { it.id!! } ?: emptyList()
        val userGroups = userEntity.groups?.map(GroupEntity::id) ?: emptyList()
        val userHierarchyGroups = groupRepository.getParentGroups(userGroups.filterNotNull()).map { it.id!! }

        return if (courseGroups.any { it in userHierarchyGroups }) createCourseModel(course)
        else null
    }

    override fun getAllForUser(user: Long): List<Course> {
        val groups = groupRepository.getGroupsForUser(user).map { it.id!! }
        val hierarchyGroups = groupRepository.getParentGroups(groups)

        val courses = hierarchyGroups.map { it.id!! }.let(courseRepository::getForGroups)

        return courses.map(::createCourseModel)
    }


    override fun existsById(id: Long) = courseRepository
        .existsById(id)


    override fun create(model: Course) =
        createCourseEntity(model, emptySet(), emptySet(), emptySet())
            .let(courseRepository::save)
            .let(::createCourseModel)


    override fun delete(id: Long) = courseRepository
        .findById(id).get()
        .apply(courseRepository::delete)
        .let(::createCourseModel)


    override fun update(
        course: Course
    ): Course {
        if (!courseRepository.existsById(course.id!!)) throw IllegalStateException("Can't find course with id '${course.id!!}'")

        val leaders = courseRepository.findById(course.id!!).get().leaders!!
        val groups = courseRepository.findById(course.id!!).get().groups!!
        val timings = courseRepository.findById(course.id!!).get().timings!!

        return createCourseEntity(course, leaders, groups, timings)
            .let(courseRepository::save)
            .let(::createCourseModel)
    }

    override fun updateLeaders(id: Long, leaders: List<User>) {
        val entities = leaders.map {
            val groups = userRepository.findById(it.id!!).get().groups!!
            createUserEntity(it, groups)
        }
        val entity = courseRepository.findById(id).get()

        entity.leaders = entities.toSet()
        courseRepository.save(entity)
    }

    override fun updateGroups(id: Long, groups: List<Group>) {
        val entities = groups.map {
            createGroupEntity(it)
        }
        val entity = courseRepository.findById(id).get()

        entity.groups = entities.toMutableSet()
        courseRepository.save(entity)
    }

    override fun updateTimings(id: Long, timings: List<CourseTiming>) {
        val entities = timings.map {
            createCourseTimingEntity(it)
        }
        val entity = courseRepository.findById(id).get()

        entity.timings = entities.toSet()
        courseRepository.save(entity)
    }

    override fun getAllForGroup(groupId: Long): List<Course> {
        val groups = groupRepository.getParentGroups(groupId).map { it.id!! }

        val courses = groups.let(courseRepository::getForGroups)

        return courses.map(::createCourseModel)
    }

    override fun getLeaders(id: Long) = courseRepository
        .findById(id).get()
        .leaders!!
        .map { user ->
            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

            createUserModel(user, perms)
        }
        .toSet()

    override fun getGroups(id: Long) = courseRepository
        .findById(id).get()
        .groups!!
        .map(::createGroupModel).toSet()

    override fun getTimings(id: Long) = courseRepository
        .findById(id).get()
        .timings!!
        .map(::createCourseTimingModel).toSet()


}