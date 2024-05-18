package com.untis.domain.user

import com.untis.domain.BaseUseCase
import com.untis.model.CourseInstanceInfo
import com.untis.service.CourseInstanceInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * A [BaseUseCase] which returns all the courses a user has in a specific interval of time.
 */
@Component
class GetCourseInstancesInTime @Autowired constructor(

    val instanceService: CourseInstanceInfoService

) : BaseUseCase<GetCourseInstancesInTime.Input, List<CourseInstanceInfo>> {

    /**
     * The input for this use case
     */
    data class Input(

        /**
         * The start of the time interval
         */
        val from: LocalDateTime,

        /**
         * The end of the time interval
         */
        val to: LocalDateTime,
    )

    override operator fun invoke(input: Input) = instanceService
        .getAll()
        .filter { info ->
            LocalDateTime.of(info.date, info.startTime).let {
                it.isAfter(input.from) && it.isBefore(input.to)
            }
        }

}