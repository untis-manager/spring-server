package com.untis.controller.body.request.group

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateGroupUsersRequest (

    @JsonProperty("users")
    val users: List<Long>

)
