package com.untis.database.repository.crud

import com.untis.database.entity.CourseEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
internal interface CourseCrudRepository : CrudRepository<CourseEntity, Long> {

    @Query("""
        SELECT DISTINCT c
        FROM courses c
        INNER JOIN c.groups g
        WHERE g in (
            SELECT u.groups FROM users u WHERE u.id = :userId
        )
    """)
    fun getAllForUser(userId: Long): List<CourseEntity>

}