package com.untis.controller.body.request.settings

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateSignUpModeRequest (

    @JsonProperty("mode")
    val mode: String,

    /*
    For 'free'
     */
    @JsonProperty("emailVerification")
    val emailVerification: Boolean?,

    @JsonProperty("defaultRoleId")
    val defaultGroupId: Long?

)
