package com.untis.controller.body.request.personal

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailValidationTokenRequest (

    @JsonProperty("email")
    val email: String

)