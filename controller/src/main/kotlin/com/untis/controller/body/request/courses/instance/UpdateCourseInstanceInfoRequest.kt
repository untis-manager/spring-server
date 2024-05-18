package com.untis.controller.body.request.courses.instance

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCourseInstanceInfoRequest (

    @JsonProperty("notes")
    val notes: String

)
