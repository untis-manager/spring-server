package com.untis.controller.body.response.course

import com.untis.model.CourseInstanceInfo
import java.time.LocalDate
import java.time.LocalTime

data class CourseInstanceInfoResponse(

    val id: Long,

    val notes: String,

    val date: LocalDate,

    val startTime: LocalTime,

    val courseId: Long,

    val isCancelled: Boolean,

    val changedLocation: String?,

    val changedLeaders: List<Long>?

) {

    companion object {

        fun create(instance: CourseInstanceInfo, changedLeaders: List<Long>?) = CourseInstanceInfoResponse(
            instance.id!!,
            instance.notes,
            instance.date,
            instance.startTime,
            instance.course.id!!,
            instance.isCancelled,
            instance.changedLocation,
            changedLeaders
        )

    }

}
