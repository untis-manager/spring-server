package com.untis.controller.body.request

import com.fasterxml.jackson.annotation.JsonProperty

data class VerifyEmailRequest (

    @JsonProperty("email")
    val email: String

)
