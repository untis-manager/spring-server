package com.untis.service

import com.untis.model.*
import com.untis.service.base.BaseService

/**
 * Service to perform operations on groups.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface GroupService : BaseService<Group> {

    /**
     * Gets the users in this group
     *
     * @param id The id of the group
     * @return The users in that group
     */
    fun getUsers(id: Long): Set<User>

    /**
     * Gets the parent group of the group.
     *
     * @param id The id of the group to get the parent group of
     * @return The parent group, or null, if the group for [id] is at the top of the group hierarchy
     */
    fun getParentGroup(id: Long): Group?

    /**
     * Gets the parent groups of the group.
     *
     * @param id The id of the group to get the parent groups of
     * @return The parent groups. Also contains the group itself.
     */
    fun getParentGroups(id: Long): List<Group>

    /**
     * Gets the groups that are children of a specific group
     *
     * @param id The id of the group to get the children of
     * @return The children of the group
     */
    fun getDirectChildrenGroups(id: Long): Set<Group>

    /**
     * Gets the permissions ultimately applied to a user that is part of a specific group.
     *
     * @param id The id of the group
     * @return The permissions that are gained through the group with [id] combined with those of the parents. Higher valued permissions overwrite lower valued ones.
     */
    fun getMergedPermissions(id: Long): PermissionsBundle

    /**
     * Gets the permissions resulting from a user being included in multiple groups
     *
     * @param ids The ids of the groups a user is art of
     * @return The permissions that result from the membership in the groups with [ids]
     */
    fun getMergedPermissions(ids: List<Long>): PermissionsBundle


    /**
     * Gets the courses associated with this group
     *
     * @param id The id of the group
     * @return All the courses this group is associated with
     */
    fun getCourses(id: Long): Set<Course>


    /**
     * Updates the group with the given role
     *
     * Identifies the group to replace with [Group.id]
     *
     * @param group The group
     */
    fun update(group: Group): Group

    /**
     * Updates the users that are part of the group
     *
     * @param id The id of the group
     * @param users The ids of the new users
     */
    fun updateUsers(id: Long, users: List<Long>)

    /**
     * Updates the courses that are associated with the group
     *
     * @param id The id of the group
     * @param courses The ids of the new courses
     */
    fun updateCourses(id: Long, courses: List<Long>)
}