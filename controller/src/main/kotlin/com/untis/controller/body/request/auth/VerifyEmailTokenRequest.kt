package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class VerifyEmailTokenRequest(

    @JsonProperty("token")
    val token: String,

    @JsonProperty("email")
    val email: String

)
