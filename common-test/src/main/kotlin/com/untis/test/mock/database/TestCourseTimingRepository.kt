package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.CourseTimingEntity
import com.untis.database.repository.CourseTimingRepository
import java.util.*

class TestCourseTimingRepository : CourseTimingRepository {

    private val database = mutableListOf<CourseTimingEntity>()

    private val random = Random()

    override fun save(entity: CourseTimingEntity) = entity.let {
        it.id = random.nextLong()
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<CourseTimingEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: CourseTimingEntity) {
        database.remove(entity)
    }
}