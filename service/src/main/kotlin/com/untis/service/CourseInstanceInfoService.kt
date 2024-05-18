package com.untis.service

import com.untis.model.CourseInstanceInfo
import com.untis.model.User
import com.untis.service.base.BaseService
import java.time.LocalDate
import java.time.LocalTime

/**
 * Service to perform operations on course instance infos.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface CourseInstanceInfoService : BaseService<CourseInstanceInfo> {

    /**
     * Gets all instance infos by the corresponding course id
     *
     * @param courseId The id of the course
     * @return The instance infos for that course
     */
    fun getByCourse(courseId: Long): Set<CourseInstanceInfo>

    /**
     * Gets one specific course instance info
     *
     * @param courseId The id of the course
     * @param date The date of it
     * @param startTime The start time of it
     *
     * @return The Course Instance info or null, if it was not found
     */
    fun getByCourseDefinitive(
        courseId: Long,
        date: LocalDate,
        startTime: LocalTime,
    ): CourseInstanceInfo?

    /**
     * Updates a course instance info
     *
     * @param instanceInfo The instance info
     *
     * @return The updated CourseInstanceInfo
     */
    fun update(instanceInfo: CourseInstanceInfo, ): CourseInstanceInfo

    /**
     * Updates the changed leaders of a specific instance info
     *
     * @param id The id of the instance info
     * @param changedLeaders The ids of the users that will be the changed leaders, or null
     * @return The instance info
     */
    fun updateChangedLeaders(id: Long, changedLeaders: List<Long>?): CourseInstanceInfo

    /**
     * Gets the changed leaders for a specific instance info
     *
     * @param id The id of the instance info
     * @return The changed leaders, or null if none were set
     */
    fun changedLeadersFor(id: Long): List<User>?
}