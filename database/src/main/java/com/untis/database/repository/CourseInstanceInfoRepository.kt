package com.untis.database.repository

import com.untis.database.entity.CourseInstanceInfoEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

/**
 * Repository to access the entries of the [CourseInstanceInfoEntity] table
 */
@Repository
interface CourseInstanceInfoRepository: SimpleRepository<CourseInstanceInfoEntity> {

    fun getAllForCourse(courseId: Long): List<CourseInstanceInfoEntity>

    fun getByDefinition(courseId: Long, date: LocalDate, start: LocalTime): List<CourseInstanceInfoEntity>

}