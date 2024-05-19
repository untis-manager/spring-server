package com.untis.service.impl

import com.untis.database.entity.GroupEntity
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.GroupPermissionsRepository
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.UserRepository
import com.untis.model.Course
import com.untis.model.Group
import com.untis.model.PermissionsBundle
import com.untis.model.User
import com.untis.service.GroupService
import com.untis.service.mapping.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseGroupService @Autowired constructor(

    val groupRepository: GroupRepository,

    val userRepository: UserRepository,

    val courseRepository: CourseRepository,

    val groupPermissionsRepository: GroupPermissionsRepository
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
            if (userInGroup(user, id)) group
            else null
        }
        ?.let(::createGroupModel)

    // Checks also for indirect
    private fun userInGroup(groupId: Long, userId: Long): Boolean {
        val user = userRepository.findById(userId).get()
        val children = groupRepository.getChildrenGroups(groupId).map { it.id!! }

        return user.groups!!.any { it.id == groupId || it.id in children }
    }

    override fun getAllForUser(user: Long): List<Group> = groupRepository
        .getGroupsForUser(user)
        .map(::createGroupModel)


    override fun getUsers(id: Long): Set<User> {
        val groups = groupRepository.getChildrenGroups(id)
        val usersPerGroup = groups.map { userRepository.getUsersInGroups(listOf(it.id!!)) }
        return usersPerGroup
            .flatten()
            .distinctBy { it.id!! }
            .map { user ->
                val perms = getMergedPermissions(user.groups!!.map { it.id!! })

                createUserModel(user, perms)
            }
            .toSet()
    }

    override fun getParentGroup(id: Long): Group? =
        groupRepository
            .findById(id).get()
            .parentGroup
            ?.let(::createGroupModel)

    override fun getParentGroups(id: Long): List<Group> =
        groupRepository
            .getParentGroups(id)
            .map(::createGroupModel)

    override fun getDirectChildrenGroups(id: Long): Set<Group> =
        groupRepository
            .findById(id).get()
            .directChildrenGroups!!
            .map(::createGroupModel)
            .toSet()

    override fun getMergedPermissions(id: Long): PermissionsBundle {
        val allGroups = groupRepository.getParentGroups(id) // In order: group, parent1, parent2, etc...
        val iter = allGroups.iterator()

        var partialBundle = createPartialPermissionBundleModel(iter.next().permissions!!)

        while(iter.hasNext()) {
            val nextBundle = createPartialPermissionBundleModel(iter.next().permissions!!)

            // We start from child upwards to parent, so to prioritize children permissions, we pass 'partialBundle' as 'other'
            partialBundle = nextBundle.mergeWith(partialBundle)
        }

        if(!partialBundle.isFullBundle()) {
            throw IllegalStateException("Could not create a full permission bundle for group with id = $id as some fields would be unset.")
        }

        return partialBundle.toFullBundle()
    }

    override fun getMergedPermissions(ids: List<Long>): PermissionsBundle {
        val groups = groupRepository.findAllById(ids)
        val fullPermissions = groups.map { getMergedPermissions(it!!.id!!) }

        val permissions = fullPermissions.fold(PermissionsBundle.Worst) { merged, next ->
            merged.mergeWith(next)
        }

        return permissions
    }


    override fun getCourses(id: Long): Set<Course> {
        val groups = groupRepository.getParentGroups(id)

        return groups.map { group ->
            groupRepository
                .getCoursesForGroup(group.id!!)
                .map(::createCourseModel)
        }.flatten()
            .distinctBy { it.id }
            .toSet()
    }


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