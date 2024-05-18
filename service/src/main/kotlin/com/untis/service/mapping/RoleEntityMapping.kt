package com.untis.service.mapping

import com.untis.database.entity.RoleEntity
import com.untis.model.Permission
import com.untis.model.Role
import com.untis.model.UserPermissions

internal fun createRoleModel(entity: RoleEntity): Role = Role(
    id = entity.id!!,
    name = entity.name!!,
    permissions = entity.extractUserPermissions()
)

internal fun RoleEntity.extractUserPermissions(): UserPermissions = UserPermissions(
    users = Permission.Users.create(permissionUsers!!),
    profile = Permission.Profile.create(permissionProfile!!),
    roles = Permission.Scoped.create(permissionRoles!!),
    courses = Permission.Scoped.create(permissionCourses!!),
    groups = Permission.Scoped.create(permissionGroups!!),
    serverSettings = Permission.Simple.create(permissionServerSettings!!),
    announcements = Permission.Scoped.create(permissionAnnouncements!!)
)

internal fun createRoleEntity(model: Role): RoleEntity = RoleEntity(
    id = model.id,
    name = model.name,
    permissionUsers = model.permissions.users.stage,
    permissionProfile = model.permissions.profile.stage,
    permissionRoles = model.permissions.roles.stage,
    permissionCourses = model.permissions.courses.stage,
    permissionGroups = model.permissions.groups.stage,
    permissionServerSettings = model.permissions.serverSettings.stage,
    permissionAnnouncements = model.permissions.announcements.stage
)