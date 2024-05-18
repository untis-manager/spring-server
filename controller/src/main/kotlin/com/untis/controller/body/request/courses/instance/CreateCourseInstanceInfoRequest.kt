package com.untis.controller.body.request.courses.instance

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateCourseInstanceInfoRequest (

    @JsonProperty("notes")
    val notes: String,

    @JsonProperty("date")
    val date: String,

    @JsonProperty("startTime")
    val startTime: String,

    @JsonProperty("courseId")
    val courseId: Long,

    @JsonProperty("isCancelled")
    val isCancelled: Boolean,

    @JsonProperty("changedLeaders")
    val changedLeaders: List<Long>?,

    @JsonProperty("changedLocation")
    val changedLocation: String?

)
