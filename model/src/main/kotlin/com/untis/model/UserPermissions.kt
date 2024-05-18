package com.untis.model

/**
 * Contain all permissions of a user over different scopes
 */
data class UserPermissions(

    /**
     * Permission to interact with other users
     */
    val users: Permission.Users,

    /**
     * Permission to interact with the own profile
     */
    val profile: Permission.Profile,

    /**
     * Permission to interact with courses
     */
    val courses: Permission.Scoped,

    /**
     * Permission to interact with groups
     */
    val groups: Permission.Scoped,

    /**
     * Permission to interact with roles
     */
    val roles: Permission.Scoped,

    /**
     * Permission to interact with server settings
     */
    val serverSettings: Permission.Simple,

    /**
     * Permission to interact with announcements
     */
    val announcements: Permission.Scoped

) {

    /**
     * Returns all singular permissions
     */
    fun all(): Set<Permission> = setOf(users, profile, courses, groups, roles, serverSettings, announcements)

    fun allWithPrefix(): Map<String, Permission> = mapOf(
        "USERS" to users,
        "PROFILE" to profile,
        "COURSES" to courses,
        "GROUPS" to groups,
        "ROLES" to roles,
        "SERVER_SETTINGS" to serverSettings,
        "ANNOUNCEMENTS" to announcements
    )

    companion object {

        val Admin = UserPermissions(
            users = Permission.Users.Edit,
            profile = Permission.Profile.Edit,
            courses = Permission.Scoped.Edit,
            groups = Permission.Scoped.Edit,
            roles = Permission.Scoped.Edit,
            serverSettings = Permission.Simple.Write,
            announcements = Permission.Scoped.Edit
        )

    }

}
