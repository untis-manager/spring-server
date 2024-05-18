package com.untis.database.repository.crud

import com.untis.database.entity.CourseTimingEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
internal interface CourseTimingCrudRepository : CrudRepository<CourseTimingEntity, Long>