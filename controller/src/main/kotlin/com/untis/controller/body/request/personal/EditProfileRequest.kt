package com.untis.controller.body.request.personal

import com.fasterxml.jackson.annotation.JsonProperty

data class EditProfileRequest(

    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("birthday")
    val birthday: String,

    @JsonProperty("gender")
    val gender: String,

    @JsonProperty("country")
    val country: String,

    @JsonProperty("city")
    val city: String,

    @JsonProperty("zip")
    val zip: String,

    @JsonProperty("street")
    val street: String,

    @JsonProperty("houseNumber")
    val houseNumber: String

)
