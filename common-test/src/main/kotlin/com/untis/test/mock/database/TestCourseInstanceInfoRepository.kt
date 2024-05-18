package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.CourseInstanceInfoEntity
import com.untis.database.repository.CourseInstanceInfoRepository
import java.util.*

class TestCourseInstanceInfoRepository : CourseInstanceInfoRepository {

    private val database = mutableListOf<CourseInstanceInfoEntity>()

    private val random = Random()

    override fun save(entity: CourseInstanceInfoEntity) = entity.let {
        it.id = random.nextLong()
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<CourseInstanceInfoEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: CourseInstanceInfoEntity) {
        database.remove(entity)
    }
}