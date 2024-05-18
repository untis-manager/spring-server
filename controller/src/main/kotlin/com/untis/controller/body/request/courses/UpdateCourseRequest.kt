package com.untis.controller.body.request.courses

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCourseRequest(

    @JsonProperty("name")
    val name: String,

    @JsonProperty("description")
    val description: String

)
