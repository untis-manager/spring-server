package com.untis.service

import com.untis.model.Course
import com.untis.model.Group
import com.untis.model.User
import com.untis.service.base.BaseService

/**
 * Service to perform operations on groups.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface GroupService: BaseService<Group> {


    /**
     * Gets the users in this group
     *
     * @param id The id of the group
     * @return The users in that group
     */
    fun getUsers(id: Long): Set<User>


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