package com.untis.database.repository.crud

import com.untis.database.entity.CourseInstanceInfoEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

@Repository
internal interface CourseInstanceInfoCrudRepository : CrudRepository<CourseInstanceInfoEntity, Long> {

    @Query("""
        SELECT i
        FROM course_instance_info i
        WHERE i.course.id = :courseId
    """)
    fun getAllForCourse(courseId: Long): List<CourseInstanceInfoEntity>

    @Query("""
        SELECT i
        from course_instance_info i
        WHERE i.course.id = :courseId AND i.startTime = :startTime AND i.date = :date
    """)
    fun getByDefinition(courseId: Long, date: LocalDate, start: LocalTime): List<CourseInstanceInfoEntity>

}