package com.untis.controller.body.request.courses.timing

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCourseTimingRequest (

    @JsonProperty("start")
    val start: String,

    @JsonProperty("end")
    val end: String,

    @JsonProperty("breakTime")
    val breakTime: Int,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("location")
    val location: String,

    // 'single' or 'repeating'
    @JsonProperty("mode")
    val mode: String,

    /*
    For single
     */

    @JsonProperty("date")
    val date: String?,

    /*
    For repeating
     */

    @JsonProperty("firstDate")
    val firstDate: String?,

    @JsonProperty("dayInterval")
    val dayInterval: Int?,

    @JsonProperty("limit")
    val limit: RepeatingCourseTimingLimitRequest?

)