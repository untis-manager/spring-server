package com.untis.model

import java.time.LocalDate

/**
 * Defines how long a [CourseTiming] will go on
 */
sealed class RepeatingCourseTimingLimit {

    /**
     * Will end on a specific date
     */
    data class Absolute(

        /**
         * The date it will end on
         */
        val end: LocalDate

    ): RepeatingCourseTimingLimit()

    /**
     * Will only repeat a certain amount of time
     */
    data class RepeatingLimit(

        /**
         * The amount of times it will be scheduled for
         */
        val repetitions: Int,

    ): RepeatingCourseTimingLimit()

}