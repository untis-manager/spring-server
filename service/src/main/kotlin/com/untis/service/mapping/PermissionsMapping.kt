package com.untis.service.mapping

import com.untis.database.entity.GroupPermissionsEntity
import com.untis.model.Permission
import com.untis.model.PermissionsBundle

internal fun createPermissionBundleModel(permission: GroupPermissionsEntity): PermissionsBundle = PermissionsBundle(
    users = Permission.Users.create(permission.permissionUsers!!),
    profile = Permission.Profile.create(permission.permissionProfile!!),
    courses = Permission.Scoped.create(permission.permissionCourses!!),
    groups = Permission.Scoped.create(permission.permissionGroups!!),
    roles = Permission.Scoped.create(permission.permissionRoles!!),
    serverSettings = Permission.Simple.create(permission.permissionServerSettings!!),
    announcements = Permission.Scoped.create(permission.permissionAnnouncements!!)
)