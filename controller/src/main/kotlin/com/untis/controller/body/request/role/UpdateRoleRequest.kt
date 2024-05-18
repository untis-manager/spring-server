package com.untis.controller.body.request.role

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateRoleRequest (

    @JsonProperty("name")
    val name: String,

    @JsonProperty("permissions")
    val permissions: UserPermissionsRequest

)
