package com.untis.model

/**
 * Model for a group which is a connection of several users
 */
data class Group(

    /**
     * The id of the group
     * Null when it has not yet been saved
     */
    val id: Long?,

    /**
     * The name of the group
     */
    val name: String,

    /**
     * The permissions configured for this group.
     *
     * This does NOT refer to the permissions that a user is given being part of this group.
     * These may be overwritten by permissions of a parent group.
     */
    val permissions: PermissionsBundle

)