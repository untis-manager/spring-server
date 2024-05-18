package com.untis.controller.body.request.role

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateRoleRequest (

    @JsonProperty("name")
    val name: String,

    @JsonProperty("permissions")
    val permissions: UserPermissionsRequest

)
