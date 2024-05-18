package com.untis.controller.body.response.course

import com.untis.model.RepeatingCourseTimingLimit

data class RepeatingCourseTimingLimitResponse(

    // 'repetitions' or 'absolute'
    val mode: String,

    /*
    For repetitions
     */
    val repetitions: Int?,

    /*
    For absolute
     */
    val end: String?

) {

    companion object {

        fun create(limit: RepeatingCourseTimingLimit) = limit.run {
            RepeatingCourseTimingLimitResponse(
                mode = if (limit is RepeatingCourseTimingLimit.Absolute) "absolute" else "repetitions",
                repetitions = (limit as? RepeatingCourseTimingLimit.RepeatingLimit)?.repetitions,
                end = (limit as? RepeatingCourseTimingLimit.Absolute)?.end?.toString()
            )
        }

    }

}
