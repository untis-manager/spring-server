package com.untis.database.repository

import com.untis.database.entity.CourseEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.stereotype.Repository

/**
 * Repository to access the entries of the [CourseEntity] table
 */
@Repository
interface CourseRepository: SimpleRepository<CourseEntity> {

    fun getForGroups(groupIds: List<Long>): List<CourseEntity>

}