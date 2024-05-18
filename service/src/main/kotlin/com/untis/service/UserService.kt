package com.untis.service

import com.untis.model.*
import com.untis.service.base.BaseService

/**
 * Service to perform operations on users.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface UserService : BaseService<User> {

    /**
     * Gets the user with the specified email address
     *
     * @param email The email address
     * @return The user, or null if no user matches the email
     */
    fun getByEmail(email: String): User?

    /**
     * Gets all users by a specified role
     *
     * @param roleId The id of the role to search for
     * @return All users with that role
     */
    fun getByRole(roleId: Long): Set<User>

    /**
     * Gets all in the specified group
     *
     * @param groupId The id of the group to search for
     * @return All users in that group
     */
    fun getInGroup(groupId: Long): Set<User>

    /**
     * Gets all the courses the user is enrolled in
     *
     * @param id The id of the user
     * @return All courses the user is enrolled into
     */
    fun getEnrolledCourses(id: Long): Set<Course>

    /**
     * Updates the user
     * 
     * Updates the already persisted user with [User.id] to the values in [user]
     *
     * [User.id] must not be null
     *
     * A user with [User.id] must already exist
     * 
     * @param user The user to update
     * @return The updated user
     */
    fun update(user: User): User

    /**
     * Adds the user to the specified group
     *
     * @param groupId The id of the group
     * @param userId The id of the user
     * @return The user for [userId]
     */
    fun addToGroup(groupId: Long, userId: Long): User

    /**
     * Changes the groups of a user to those in [groupIds]
     *
     * @param userId The id of the user
     * @param groupIds The ids of the groups
     * @return The groups the user is now part of
     */
    fun updateGroups(userId: Long, groupIds: Set<Long>): Set<Group>

}