package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.SecurityTokenEntity
import com.untis.database.repository.SecurityTokenRepository
import java.util.*

class TestSecurityTokenRepository : SecurityTokenRepository {

    private val database = mutableListOf<SecurityTokenEntity>()

    private val random = Random()

    override fun save(entity: SecurityTokenEntity) = entity.let {
        it.id = random.nextLong()
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<SecurityTokenEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: SecurityTokenEntity) {
        database.remove(entity)
    }
}