package com.untis.model

import java.time.LocalDate
import java.time.LocalTime

/**
 * Model that represents information about a specific instance of a [Course]
 */
data class CourseInstanceInfo(

    /**
     * The id of the [CourseInstanceInfo]
     */
    val id: Long? = null,

    /**
     * Notes about the instance
     */
    val notes: String,

    /**
     * Whether this instance is cancelled
     */
    val isCancelled: Boolean,

    /**
     * The location of this instance that may differ from the location from the course.
     *
     * Null in case the location has not yet been changed
     */
    val changedLocation: String?,

    /**
     * The changed leaders for the instance info.
     *
     * Null in case the leaders were not changed
     */
    val changedLeaders: List<User>?,

    /**
     * The date of the instance
     */
    val date: LocalDate,

    /**
     * The start time of the instance
     */
    val startTime: LocalTime,

    /**
     * The course it provides information about
     */
    val course: Course

)
