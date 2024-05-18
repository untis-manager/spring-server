package com.untis.model

import java.time.LocalDate
import java.time.LocalTime

/**
 * Model that encapsulates possible timings for a course
 */
sealed class CourseTiming(

    /**
     * The beginning of the course
     */
    open val start: LocalTime,

    /**
     * The event of the course
     */
    open val end: LocalTime,

    /**
     * The amount of break in minutes
     */
    open val breakTime: Int,

    /**
     * The name of the course
     */
    open val name: String,

    /**
     * The id of the timing
     */
    open val id: Long?,

    /**
     * The internal location where the course will take part
     */
    open val location: String
) {

    /**
     * Gets the absolute dates for this [CourseTiming]
     */
    abstract fun dates(): List<LocalDate>

    /**
     * A one-time event
     */
    data class Single(

        /**
         * The date it is scheduled to
         */
        val date: LocalDate,

        override val start: LocalTime,
        override val end: LocalTime,
        override val breakTime: Int,
        override val name: String,
        override val id: Long?,
        override val location: String
    ) : CourseTiming(start, end, breakTime, name, id, location) {

        override fun dates() = listOf(date)

    }

    /**
     * An event that repeats in even intervals
     */
    data class Repeating(

        /**
         * The date it is first scheduled on
         */
        val firstDate: LocalDate,

        /**
         * The interval in days it is repeating at
         */
        val dayInterval: Int,

        /**
         * The limit, defining how many times this is repeated
         */
        val limit: RepeatingCourseTimingLimit,

        override val start: LocalTime,
        override val end: LocalTime,
        override val breakTime: Int,
        override val name: String,
        override val id: Long?,
        override val location: String
    ) : CourseTiming(start, end, breakTime, name, id, location) {

        override fun dates() = when (limit) {
            is RepeatingCourseTimingLimit.RepeatingLimit -> (0 until limit.repetitions).map {
                this.firstDate.plusDays(it.toLong())
            }

            is RepeatingCourseTimingLimit.Absolute -> {
                val dates = mutableListOf<LocalDate>()

                var nextDate = firstDate

                while (nextDate.isBefore(limit.end)) {
                    dates.add(nextDate)
                    nextDate = nextDate.plusDays(dayInterval.toLong())
                }

                dates
            }
        }

    }

}