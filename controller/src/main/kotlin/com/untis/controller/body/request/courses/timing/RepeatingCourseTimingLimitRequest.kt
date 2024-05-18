package com.untis.controller.body.request.courses.timing

import com.fasterxml.jackson.annotation.JsonProperty

data class RepeatingCourseTimingLimitRequest (

    // 'repetitions' or 'absolute'
    @JsonProperty("mode")
    val mode: String,

    /*
    For repetitions
     */
    @JsonProperty("repetitions")
    val repetitions: Int?,

    /*
    For absolute
     */
    @JsonProperty("end")
    val end: String?

)