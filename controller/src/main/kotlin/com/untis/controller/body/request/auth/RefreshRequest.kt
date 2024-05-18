package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshRequest(

    @JsonProperty("refreshToken")
    val refreshToken: String

)
