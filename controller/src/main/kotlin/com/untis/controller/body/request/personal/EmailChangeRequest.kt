package com.untis.controller.body.request.personal

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailChangeRequest(

    @JsonProperty("newEmail")
    val newEmail: String? = null,

    @JsonProperty("oldEmail")
    val oldEmail: String? = null,

    @JsonProperty("password")
    val password: String? = null,

    @JsonProperty("token")
    val token: String? = null
)

