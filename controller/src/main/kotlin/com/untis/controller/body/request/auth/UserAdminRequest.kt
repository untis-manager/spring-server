package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

class UserAdminRequest (

    @JsonProperty("roleId")
    val roleId: Long,

    @JsonProperty("userData")
    val userData: UserRequest

)