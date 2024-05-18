package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class LogoutRequest (

    @JsonProperty("token")
    val token: String
)