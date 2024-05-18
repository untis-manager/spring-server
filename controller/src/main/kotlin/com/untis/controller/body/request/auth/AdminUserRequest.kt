package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

class AdminUserRequest(

    @JsonProperty("secret")
    val secret: String,

    @JsonProperty("userData")
    val userData: UserRequest

)
