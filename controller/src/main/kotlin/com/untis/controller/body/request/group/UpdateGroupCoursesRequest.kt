package com.untis.controller.body.request.group

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateGroupCoursesRequest(

    @JsonProperty("courses")
    val courses: List<Long>

)
