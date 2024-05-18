package com.untis.model

/**
 * Contain all permissions of a user over different scopes
 */
data class PermissionsBundle(

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

    /**
     * Merges two [PermissionsBundle]
     *
     * @param other The bundle to merge this with
     * @return A new permission bundle which for each field contains the higher ranking permission
     */
    fun mergeWith(other: PermissionsBundle): PermissionsBundle = PermissionsBundle(
        users = if(this.users > other.users) this.users else other.users,
        profile = if(this.profile > other.profile) this.profile else other.profile,
        courses = if(this.courses > other.courses) this.courses else other.courses,
        groups = if(this.groups > other.groups) this.groups else other.groups,
        roles = if(this.roles > other.roles) this.roles else other.roles,
        serverSettings = if(this.serverSettings > other.serverSettings) this.serverSettings else other.serverSettings,
        announcements = if(this.announcements > other.announcements) this.announcements else other.announcements
    )

    companion object {

        val Best = PermissionsBundle(
            users = Permission.Users.Edit,
            profile = Permission.Profile.Edit,
            courses = Permission.Scoped.Edit,
            groups = Permission.Scoped.Edit,
            roles = Permission.Scoped.Edit,
            serverSettings = Permission.Simple.Write,
            announcements = Permission.Scoped.Edit
        )

        val Worst = PermissionsBundle (
            users = Permission.Users.Not,
            profile = Permission.Profile.Not,
            courses = Permission.Scoped.Not,
            groups = Permission.Scoped.Not,
            roles = Permission.Scoped.Not,
            serverSettings = Permission.Simple.Read,
            announcements = Permission.Scoped.Not
        )

    }

}
