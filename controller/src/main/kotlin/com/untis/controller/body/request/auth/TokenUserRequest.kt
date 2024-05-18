package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

class TokenUserRequest (

    @JsonProperty("token")
    val token: String,

    @JsonProperty("userData")
    val userData: UserRequest

)