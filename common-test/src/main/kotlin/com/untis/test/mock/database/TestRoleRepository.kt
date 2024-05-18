package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.RoleEntity
import com.untis.database.repository.RoleRepository
import java.util.*

class TestRoleRepository : RoleRepository {

    private val database = mutableListOf<RoleEntity>()

    private val random = Random()

    override fun save(entity: RoleEntity) = entity.let {
        it.id = random.nextLong()
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<RoleEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: RoleEntity) {
        database.remove(entity)
    }
}