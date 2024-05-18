package com.untis.controller.body.request.role

import com.fasterxml.jackson.annotation.JsonProperty

data class PermissionRequest (

    @JsonProperty("type")
    val type: String,

    @JsonProperty("stage")
    val stage: Int

)
