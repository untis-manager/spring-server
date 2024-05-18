package com.untis.model

/**
 * Model that represents a role a user can be assigned to, that identifies the users permissions in the system.
 */
data class Role(

    /**
     * The id of the role
     * Null when it has not yet been saved
     */
    val id: Long? = null,

    /**
     * The name of the role
     */
    val name: String,

    /**
     * The permissions given to the role
     */
    val permissions: UserPermissions,

)