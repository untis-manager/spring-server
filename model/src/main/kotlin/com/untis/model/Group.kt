package com.untis.model

/**
 * Model for a group which is a connection of several users
 */
data class Group (

    /**
     * The id of the group
     * Null when it has not yet been saved
     */
    val id: Long?,

    /**
     * The name of the group
     */
    val name: String,

)