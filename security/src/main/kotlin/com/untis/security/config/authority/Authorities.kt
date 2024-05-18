package com.untis.security.config.authority

import com.untis.model.Permission

/*
 * Helper functions to artificially create authorities
 */

internal fun profile(permission: Permission.Profile): Array<String> =
    genFor("PROFILE", permission)

internal fun role(permission: Permission.Scoped): Array<String> =
    genFor("ROLES", permission)

internal fun courses(permission: Permission.Scoped): Array<String> =
    genFor("COURSES", permission)

internal fun announcements(permission: Permission.Scoped): Array<String> =
    genFor("ANNOUNCEMENTS", permission)

internal fun groups(permission: Permission.Scoped): Array<String> =
    genFor("GROUPS", permission)

internal fun serverSettings(permission: Permission.Simple): Array<String> =
    genFor("SERVER_SETTINGS", permission)

private fun genFor(name: String, permission: Permission): Array<String> =
    permission.authoritiesUpwards(name).toTypedArray()