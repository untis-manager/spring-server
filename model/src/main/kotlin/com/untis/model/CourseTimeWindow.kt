package com.untis.model

import java.time.LocalDate
import java.time.LocalTime

/**
 * The time window a course may occupy
 */
data class CourseTimeWindow(

    /**
     * The date it is scheduled on
     */
    val date: LocalDate,

    /**
     * The beginning of the course
     */
    val begin: LocalTime,

    /**
     * The end of the course
     */
    val end: LocalTime

)