package com.untis.service.impl

import com.untis.database.repository.CourseRepository
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.UserRepository
import com.untis.model.Course
import com.untis.model.User
import com.untis.service.GroupService
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

    val courseRepository: CourseRepository,

    val groupRepository: GroupRepository,

    val groupService: GroupService
) : UserService {

    override fun getAll(): Set<User> = userRepository
        .findAll()
        .map { user ->
            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

            createUserModel(user, perms)
        }
        .toSet()

    override fun getById(id: Long): User = userRepository
        .findById(id).get()
        .let { user ->
            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

            createUserModel(user, perms)
        }

    override fun getByIdAndUser(id: Long, user: Long): User? {
        throw NotImplementedError("Can't get a user specific to a user")
    }

    override fun getAllForUser(user: Long): List<User> {
        throw NotImplementedError("Can't get a user specific to a user")
    }

    override fun getByEmail(email: String): User? = userRepository
        .getByEmail(email).orElse(null)
        ?.let { user ->
            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

            createUserModel(user, perms)
        }

    override fun getInGroup(groupId: Long): Set<User> {
        val groups = groupRepository.getChildrenGroups(groupId)

        return userRepository
            .getUsersInGroups(groups.map { it.id!! })
            .map { user ->
                val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                createUserModel(user, perms)
            }
            .toSet()
    }

    override fun existsById(id: Long): Boolean = userRepository
        .findById(id)
        .isPresent

    override fun getEnrolledCourses(
        id: Long
    ): Set<Course> {
        val groupsUser = userRepository.findById(id).get().groups!!
        val groups = groupRepository.getParentGroups(groupsUser.map { it.id!! })

        return courseRepository.getForGroups(groups.map { it.id!! })
            .map(::createCourseModel)
            .toSet()
    }

    override fun create(user: User, groupId: Long): User {
        val group = groupRepository.findById(groupId).get()
        val permissions = groupService.getMergedPermissions(group.id!!)

        val entity = createUserEntity(
            model = user,
            groupEntities = setOf(group)
        )

        return createUserModel(entity, permissions)
    }

    override fun create(model: User): User {
        throw IllegalStateException("Use the overload with the 'courseId' parameter")
    }

    override fun delete(id: Long): User = userRepository
        .findById(id).get()
        .apply(userRepository::delete)
        .let { user ->
            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

            createUserModel(user, perms)
        }

    override fun update(user: User): User {
        if (!userRepository.existsById(user.id!!)) throw IllegalStateException("User with id '${user.id}' not found")

        val groups = userRepository.findById(user.id!!).get().groups!!

        return createUserEntity(user, groups)
            .let(userRepository::save)
            .let { user ->
                val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                createUserModel(user, perms)
            }
    }

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