package com.untis.database.repository

import com.untis.database.entity.CourseTimingEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.stereotype.Repository

/**
 * Repository to access the entries of the [CourseTimingEntity] table
 */
@Repository
interface CourseTimingRepository : SimpleRepository<CourseTimingEntity>