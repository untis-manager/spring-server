package com.untis.controller.body.request.courses

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCourseGroupsRequest (

    @JsonProperty("groups")
    val groups: List<Long>

)
