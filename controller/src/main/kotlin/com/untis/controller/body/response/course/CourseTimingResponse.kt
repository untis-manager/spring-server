package com.untis.controller.body.response.course

import com.untis.model.CourseTiming

data class CourseTimingResponse(

    val id: Long,

    val start: String,

    val end: String,

    val breakTime: Int,

    val name: String,

    val location: String,

    // 'single' or 'repeating'
    val mode: String,

    /*
    For single
     */

    val date: String?,

    /*
    For repeating
     */

    val firstDate: String?,

    val dayInterval: Int?,

    val limit: RepeatingCourseTimingLimitResponse?

) {

    companion object {

        fun create(timing: CourseTiming) = when (timing) {
            is CourseTiming.Repeating -> CourseTimingResponse(
                id = timing.id!!,
                end = timing.end.toString(),
                start = timing.start.toString(),
                breakTime = timing.breakTime,
                name = timing.name,
                mode = "repeating",
                date = null,
                firstDate = timing.firstDate.toString(),
                dayInterval = timing.dayInterval,
                limit = RepeatingCourseTimingLimitResponse.create(timing.limit),
                location = timing.location
            )
            is CourseTiming.Single -> CourseTimingResponse(
                id = timing.id!!,
                end = timing.end.toString(),
                start = timing.start.toString(),
                breakTime = timing.breakTime,
                name = timing.name,
                mode = "single",
                date = timing.date.toString(),
                firstDate = null,
                dayInterval = null,
                limit = null,
                location = timing.location
            )
        }

    }

}
