package com.untis.controller.body.request.courses

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCourseTimingsRequest (

    @JsonProperty("timings")
    val timings: List<Long>

)
