package com.untis.service.impl

import com.untis.database.entity.GroupEntity
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.RoleRepository
import com.untis.database.repository.UserRepository
import com.untis.model.Course
import com.untis.model.User
import com.untis.service.UserService
import com.untis.service.mapping.createCourseModel
import com.untis.service.mapping.createGroupModel
import com.untis.service.mapping.createUserEntity
import com.untis.service.mapping.createUserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseUserService @Autowired constructor(

    val userRepository: UserRepository,

    val roleRepository: RoleRepository,

    val courseRepository: CourseRepository,

    val groupRepository: GroupRepository
) : UserService {

    override fun getAll(): Set<User> = userRepository
        .findAll()
        .map(::createUserModel)
        .toSet()

    override fun getById(id: Long): User = userRepository
        .findById(id).get()
        .let(::createUserModel)

    override fun getByIdAndUser(id: Long, user: Long): User? {
        throw NotImplementedError("Can't get a user specific to a user")
    }

    override fun getAllForUser(user: Long): List<User> {
        throw NotImplementedError("Can't get a user specific to a user")
    }

    override fun getByEmail(email: String): User? = userRepository
        .getByEmail(email).orElse(null)
        ?.let(::createUserModel)

    override fun getByRole(roleId: Long): Set<User> = userRepository
        .getByRole(roleId)
        .map(::createUserModel)
        .toSet()

    override fun getInGroup(groupId: Long): Set<User> = userRepository
        .getUsersInGroup(groupId)
        .map(::createUserModel)
        .toSet()

    override fun existsById(id: Long): Boolean = userRepository
        .findById(id)
        .isPresent

    override fun getEnrolledCourses(
        id: Long
    ) = courseRepository
        .getAllForUser(id)
        .map(::createCourseModel)
        .toSet()

    override fun create(model: User): User {
        val role = roleRepository.findById(model.role.id!!).get()

        val entity = createUserEntity(model, role, emptySet())

        return userRepository
            .save(entity)
            .let(::createUserModel)
    }

    override fun delete(id: Long): User = userRepository
        .findById(id).get()
        .apply(userRepository::delete)
        .let(::createUserModel)

    override fun update(user: User): User {
        if (!userRepository.existsById(user.id!!)) throw IllegalStateException("User with id '${user.id}' not found")

        val role = userRepository.findById(user.id!!).get().role!!
        val groups = userRepository.findById(user.id!!).get().groups!!

        return createUserEntity(user, role, groups)
            .let(userRepository::save)
            .let(::createUserModel)
    }

    override fun addToGroup(groupId: Long, userId: Long) = groupRepository
        .findById(groupId).get()
        .let { group ->
            userRepository
                .findById(userId).get()
                .apply { groups?.add(group) }
                .let(userRepository::save)
        }.let(::createUserModel)

    override fun updateGroups(userId: Long, groupIds: Set<Long>) = groupRepository
        .findAllById(groupIds.toList())
        .filterNotNull()
        .also { groups ->
            userRepository
                .findById(userId).get()
                .also { it.groups = groups.toMutableSet() }
                .let(userRepository::save)
        }.map(::createGroupModel)
        .toSet()

}