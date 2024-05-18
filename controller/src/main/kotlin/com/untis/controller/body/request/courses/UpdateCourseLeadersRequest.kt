package com.untis.controller.body.request.courses

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCourseLeadersRequest (

    @JsonProperty("leaders")
    val leaders: List<Long>

)
