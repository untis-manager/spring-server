package com.untis.service

import com.untis.model.CourseTiming
import com.untis.service.base.BaseService

/**
 * Service to perform operations on course timings.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface CourseTimingService: BaseService<CourseTiming> {

    /**
     * Updates the timing
     *
     * @param timing The timing
     * @return The updated timing
     */
    fun update(timing: CourseTiming): CourseTiming

}