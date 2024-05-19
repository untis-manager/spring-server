package com.untis.model

/**
 * Contain all permissions that can be configured for a group.
 *
 * Null fields mean to inherit the permission from the parent group.
 */
data class PartialPermissionsBundle(

    /**
     * Permission to interact with other users
     */
    val users: Permission.Users?,

    /**
     * Permission to interact with the own profile
     */
    val profile: Permission.Profile?,

    /**
     * Permission to interact with courses
     */
    val courses: Permission.Scoped?,

    /**
     * Permission to interact with groups
     */
    val groups: Permission.Scoped?,

    /**
     * Permission to interact with roles
     */
    val roles: Permission.Scoped?,

    /**
     * Permission to interact with server settings
     */
    val serverSettings: Permission.Simple?,

    /**
     * Permission to interact with announcements
     */
    val announcements: Permission.Scoped?

) {

    /**
     * Merges two [PartialPermissionsBundle].
     *
     * The receiver of this call is supposed to be the bundle of a group that's a parent of [other]
     *
     * @param other The bundle to merge this with. When both fields are set, [other]'s field takes priority.
     * @return A new permission bundle which for each field contains the higher ranking permission
     */
    fun mergeWith(other: PartialPermissionsBundle): PartialPermissionsBundle = PartialPermissionsBundle(
        users = decide(other) { users },
        profile = decide(other) { profile },
        courses = decide(other) { courses },
        groups = decide(other) { groups },
        roles = decide(other) { roles },
        serverSettings = decide(other) { serverSettings },
        announcements = decide(other) { announcements },
    )

    /**
     * Creates a [PermissionsBundle] from this partial one
     *
     * Requires all fields to be non-null
     */
    fun toFullBundle() =
        PermissionsBundle(users!!, profile!!, courses!!, groups!!, roles!!, serverSettings!!, announcements!!)

    /**
     * Checks whether this is already fully configured
     */
    fun isFullBundle() = users != null || profile != null || courses != null || groups != null || roles != null

    private fun <Type : Permission> PartialPermissionsBundle.decide(
        other: PartialPermissionsBundle,
        selector: PartialPermissionsBundle.() -> Type?
    ): Type? = decide(this.selector(), other.selector())

    private fun <Type : Permission> decide(first: Type?, second: Type?) =
        when (first) {
            null -> when (second) {
                null -> null
                else -> second
            }

            else -> when (second) {
                null -> first
                else -> second
            }
        }

}
