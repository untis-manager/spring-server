package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.GroupEntity
import com.untis.database.repository.GroupRepository
import java.util.*

class TestGroupRepository : GroupRepository {

    private val database = mutableListOf<GroupEntity>()

    private val random = Random()

    override fun save(entity: GroupEntity) = entity.let {
        it.id = random.nextLong()
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<GroupEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: GroupEntity) {
        database.remove(entity)
    }
}