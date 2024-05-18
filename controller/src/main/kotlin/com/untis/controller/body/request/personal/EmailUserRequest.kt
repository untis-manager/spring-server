package com.untis.controller.body.request.personal

import com.fasterxml.jackson.annotation.JsonProperty
import com.untis.controller.body.request.auth.UserRequest

class EmailUserRequest(

    @JsonProperty("emailVerificationToken")
    val emailVerificationToken: String?,

    @JsonProperty("userData")
    val userData: UserRequest

)