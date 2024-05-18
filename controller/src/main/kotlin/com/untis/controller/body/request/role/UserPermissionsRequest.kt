package com.untis.controller.body.request.role

import com.fasterxml.jackson.annotation.JsonProperty

data class UserPermissionsRequest (

    @JsonProperty("users")
    val users: PermissionRequest,

    @JsonProperty("profile")
    val profile: PermissionRequest,

    @JsonProperty("courses")
    val courses: PermissionRequest,

    @JsonProperty("roles")
    val roles: PermissionRequest,

    @JsonProperty("serverSettings")
    val serverSettings: PermissionRequest,

    @JsonProperty("groups")
    val groups: PermissionRequest,

    @JsonProperty("announcements")
    val announcements: PermissionRequest
)
