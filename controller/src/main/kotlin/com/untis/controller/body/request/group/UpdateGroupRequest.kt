package com.untis.controller.body.request.group

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateGroupRequest (

    @JsonProperty("name")
    val name: String

)
