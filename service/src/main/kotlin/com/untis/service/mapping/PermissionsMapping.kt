package com.untis.service.mapping

import com.untis.database.entity.GroupPermissionsEntity
import com.untis.model.PartialPermissionsBundle
import com.untis.model.Permission

internal fun createPartialPermissionBundleModel(permission: GroupPermissionsEntity): PartialPermissionsBundle =
    PartialPermissionsBundle(
        users = permission.permissionUsers?.let(Permission.Users::create),
        profile = permission.permissionProfile?.let(Permission.Profile::create),
        courses = permission.permissionCourses?.let(Permission.Scoped::create),
        groups = permission.permissionGroups?.let(Permission.Scoped::create),
        roles = permission.permissionRoles?.let(Permission.Scoped::create),
        serverSettings = permission.permissionServerSettings?.let(Permission.Simple::create),
        announcements = permission.permissionAnnouncements?.let(Permission.Scoped::create)
    )