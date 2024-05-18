package com.untis.database.repository.impl

import com.untis.database.entity.CourseInstanceInfoEntity
import com.untis.database.repository.CourseInstanceInfoRepository
import com.untis.database.repository.crud.CourseInstanceInfoCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

@Component
internal class DatabaseCourseInstanceInfoRepository @Autowired constructor(
    private val delegate: CourseInstanceInfoCrudRepository
) : CourseInstanceInfoRepository {

    override fun getAllForCourse(courseId: Long) = delegate.getAllForCourse(courseId)

    override fun getByDefinition(courseId: Long, date: LocalDate, start: LocalTime) =
        delegate.getByDefinition(courseId, date, start)

    override fun save(entity: CourseInstanceInfoEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<CourseInstanceInfoEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: CourseInstanceInfoEntity) = delegate.delete(entity)
}