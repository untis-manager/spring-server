package com.untis.controller.body.request

import com.fasterxml.jackson.annotation.JsonProperty

data class VerifySecretRequest (

    @JsonProperty("secret")
    val secret: String

)
