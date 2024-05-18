package com.untis.service

import com.untis.model.Course
import com.untis.model.CourseTiming
import com.untis.model.Group
import com.untis.model.User
import com.untis.service.base.BaseService

/**
 * Service to perform operations on groups.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface CourseService: BaseService<Course> {

    /**
     * Updates a course
     *
     * @param course The course
     * @return The updated course
     */
    fun update(course: Course): Course

    /**
     * Updates the leaders of the course
     *
     * @param leaders The new leaders
     */
    fun updateLeaders(id: Long, leaders: List<User>)

    /**
     * Updates the groups of the course
     *
     * @param groups The new groups
     */
    fun updateGroups(id: Long, groups: List<Group>)

    /**
     * Updates the timings of the course
     *
     * @param timings The new timings
     */
    fun updateTimings(id: Long, timings: List<CourseTiming>)

    /**
     * Gets the leaders of a course
     *
     * @param id The id of the course
     * @return The users which are leaders
     */
    fun getLeaders(id: Long): Set<User>

    /**
     * Gets the groups of a course
     *
     * @param id The id of the course
     * @return The groups of the course
     */
    fun getGroups(id: Long): Set<Group>

    /**
     * Gets the timings of a course
     *
     * @param id The id of the course
     * @return The timings
     */
    fun getTimings(id: Long): Set<CourseTiming>

}