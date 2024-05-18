package com.untis.controller.body.request.auth

import com.fasterxml.jackson.annotation.JsonProperty

open class UserRequest(

    @JsonProperty("email")
    val email: String,

    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("password")
    val password: String,

    @JsonProperty("country")
    val country: String,

    @JsonProperty("city")
    val city: String,

    @JsonProperty("zipCode")
    val zipCode: String,

    @JsonProperty("street")
    val street: String,

    @JsonProperty("houseNumber")
    val houseNumber: String,

    @JsonProperty("gender")
    val gender: String,

    @JsonProperty("birthday")
    val birthday: String
    
)
