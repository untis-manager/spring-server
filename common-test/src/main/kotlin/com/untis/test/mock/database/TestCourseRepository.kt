package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.CourseEntity
import com.untis.database.repository.CourseRepository
import java.util.*

class TestCourseRepository : CourseRepository {

    private val database = mutableListOf<CourseEntity>()

    private val random = Random()

    override fun save(entity: CourseEntity) = entity.let {
        if(it.id == null) {
            it.id = random.nextLong()
        }
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<CourseEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: CourseEntity) {
        database.remove(entity)
    }
}