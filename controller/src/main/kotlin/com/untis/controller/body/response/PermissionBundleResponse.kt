package com.untis.controller.body.response

import com.untis.model.PermissionsBundle

data class PermissionBundleResponse (

    val profile: PermissionResponse,
    val users: PermissionResponse,
    val courses: PermissionResponse,
    val groups: PermissionResponse,
    val roles: PermissionResponse,
    val serverSettings: PermissionResponse,
    val announcements: PermissionResponse

) {

    companion object {

        fun create(model: PermissionsBundle) = PermissionBundleResponse(
            profile = PermissionResponse.create(model.profile),
            users = PermissionResponse.create(model.users),
            courses = PermissionResponse.create(model.courses),
            roles = PermissionResponse.create(model.roles),
            serverSettings = PermissionResponse.create(model.serverSettings),
            announcements = PermissionResponse.create(model.announcements),
            groups = PermissionResponse.create(model.groups)
        )

    }

}