package com.untis.controller.body.request.personal

import com.fasterxml.jackson.annotation.JsonProperty

data class PasswordChangeRequest(

    // Either
    @JsonProperty("email")
    val email: String?,

    @JsonProperty("newPassword")
    val newPassword: String?,

    // Or
    @JsonProperty("token")
    val token: String?

)
