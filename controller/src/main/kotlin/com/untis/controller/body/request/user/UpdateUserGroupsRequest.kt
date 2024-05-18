package com.untis.controller.body.request.user

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateUserGroupsRequest (

    @JsonProperty("groups")
    val groups: List<Long>

)
