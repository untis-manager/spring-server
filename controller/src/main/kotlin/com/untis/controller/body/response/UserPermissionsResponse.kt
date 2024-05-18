package com.untis.controller.body.response

import com.untis.model.UserPermissions

data class UserPermissionsResponse(

    val users: PermissionResponse,

    val profile: PermissionResponse,

    val courses: PermissionResponse,

    val groups: PermissionResponse,

    val roles: PermissionResponse,

    val serverSettings: PermissionResponse,

    val announcements: PermissionResponse

) {

    companion object {

        fun create(userPermissions: UserPermissions) = UserPermissionsResponse(
            users = PermissionResponse.create(userPermissions.users),
            profile = PermissionResponse.create(userPermissions.profile),
            courses = PermissionResponse.create(userPermissions.courses),
            roles = PermissionResponse.create(userPermissions.roles),
            serverSettings = PermissionResponse.create(userPermissions.serverSettings),
            announcements = PermissionResponse.create(userPermissions.announcements),
            groups = PermissionResponse.create(userPermissions.groups)
        )

    }

}
