package com.untis.database.repository.impl

import com.untis.database.entity.CourseEntity
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.crud.CourseCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class DatabaseCourseRepository @Autowired constructor(
    private val delegate: CourseCrudRepository
) : CourseRepository {

    override fun getForGroups(groupIds: List<Long>) = delegate.getForGroupId(groupIds)

    override fun save(entity: CourseEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<CourseEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: CourseEntity) = delegate.delete(entity)
}