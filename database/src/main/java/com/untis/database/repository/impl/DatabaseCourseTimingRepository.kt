package com.untis.database.repository.impl

import com.untis.database.entity.CourseTimingEntity
import com.untis.database.repository.CourseTimingRepository
import com.untis.database.repository.crud.CourseTimingCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
internal class DatabaseCourseTimingRepository @Autowired constructor(
    private val delegate: CourseTimingCrudRepository
) : CourseTimingRepository {
    override fun save(entity: CourseTimingEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<CourseTimingEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: CourseTimingEntity) = delegate.delete(entity)
}