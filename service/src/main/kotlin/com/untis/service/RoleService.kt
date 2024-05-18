package com.untis.service

import com.untis.model.Role
import com.untis.model.User
import com.untis.service.base.BaseService

/**
 * Service to perform operations on roles.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface RoleService : BaseService<Role> {

    /**
     * Gets all the users with the specified role
     *
     * @param roleId The id of the role to filter for
     * @return All users with that role
     */
    fun getUsersWithRole(roleId: Long): Set<User>

    /**
     * Updates a role
     *
     * Updates the already persisted role with [Role.id] to the values in [role]
     *
     * [Role.id] must not be null
     *
     * A role with [Role.id] must already exist
     *
     * @param role The role
     * @return The updated role
     */
    fun update(role: Role): Role

    /**
     * Gets the role called 'admin' or creates it if it has not yet been created.
     */
    fun createOrGetAdminRole(): Role

}