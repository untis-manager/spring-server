package com.untis.service.impl

import com.untis.database.entity.GroupEntity
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.UserRepository
import com.untis.model.Group
import com.untis.service.GroupService
import com.untis.service.mapping.createCourseModel
import com.untis.service.mapping.createGroupEntity
import com.untis.service.mapping.createGroupModel
import com.untis.service.mapping.createUserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseGroupService @Autowired constructor(

    val groupRepository: GroupRepository,

    val userRepository: UserRepository,

    val courseRepository: CourseRepository

) : GroupService {

    override fun getAll() = groupRepository
        .findAll()
        .map(::createGroupModel)
        .toSet()


    override fun getById(id: Long) = groupRepository
        .findById(id).get()
        .let(::createGroupModel)

    override fun getByIdAndUser(id: Long, user: Long): Group? = groupRepository
        .findById(id).orElse(null)
        ?.let { group ->
            val userEntity = userRepository.findById(user).get()
            if (userEntity.groups?.any { it.id == group.id } == true) group else null
        }
        ?.let(::createGroupModel)

    override fun getAllForUser(user: Long): List<Group> = groupRepository
        .getGroupsForUser(user)
        .map(::createGroupModel)


    override fun getUsers(id: Long) = userRepository
        .getUsersInGroup(id)
        .map(::createUserModel).toSet()


    override fun getCourses(id: Long) = groupRepository
        .getCoursesForGroup(id)
        .map(::createCourseModel).toSet()


    override fun existsById(id: Long) = groupRepository
        .existsById(id)


    override fun create(model: Group) = model
        .let(::createGroupEntity)
        .let(groupRepository::save)
        .let(::createGroupModel)


    override fun delete(id: Long) = groupRepository
        .findById(id).get()
        .apply(groupRepository::delete)
        .let(::createGroupModel)


    override fun update(
        group: Group
    ): Group {
        if (!groupRepository.existsById(group.id!!)) throw IllegalStateException("Can't find group with id '${group.id}'")

        return createGroupEntity(group)
            .let(groupRepository::save)
            .let(::createGroupModel)
    }

    @Suppress("DuplicatedCode")
    override fun updateUsers(id: Long, users: List<Long>) {
        val group = groupRepository.findById(id).get()

        userRepository.findAll().forEach { user ->
            val willBeInGroup = user.id in users
            val wasInGroup = id in user.groups!!.map(GroupEntity::id)

            if (willBeInGroup && !wasInGroup) {
                user.groups?.add(group)
                userRepository.save(user)
            } else if (!willBeInGroup && wasInGroup) {
                user.groups?.removeIf { it.id == id }
                userRepository.save(user)
            }
        }
    }

    @Suppress("DuplicatedCode")
    override fun updateCourses(id: Long, courses: List<Long>) {
        val group = groupRepository.findById(id).get()

        courseRepository.findAll().forEach { course ->
            val willBeInGroup = course.id in courses
            val wasInGroup = id in course.groups!!.map(GroupEntity::id)

            if (willBeInGroup && !wasInGroup) {
                course.groups?.add(group)
                courseRepository.save(course)
            } else if (!willBeInGroup && wasInGroup) {
                course.groups?.removeIf { it.id == id }
                courseRepository.save(course)
            }
        }
    }
}