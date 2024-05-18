package com.untis.controller.body.response.course

import com.untis.model.Course
import com.untis.model.CourseInstanceInfo
import com.untis.model.CourseTiming

data class CourseResponse(

    val id: Long,

    val name: String,

    val description: String,

    val timings: List<CourseTimingResponse>,

    val instanceInfos: List<CourseInstanceInfoResponse>

) {

    companion object {

        fun create(course: Course, timings: List<CourseTiming>, instanceInfos: List<CourseInstanceInfo>) =
            CourseResponse(
                id = course.id!!,
                name = course.name,
                description = course.description,
                timings = timings.map(CourseTimingResponse::create),
                instanceInfos = instanceInfos.map {
                    CourseInstanceInfoResponse.create(
                        it,
                        it.changedLeaders?.map { it.id!! })
                }
            )

    }

}
