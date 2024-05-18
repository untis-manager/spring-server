package com.untis.test.mock.database

import com.untis.common.optional
import com.untis.database.entity.UserEntity
import com.untis.database.repository.UserRepository
import java.util.*

class TestUserRepository : UserRepository {

    private val database = mutableListOf<UserEntity>()

    private val random = Random()

    override fun save(entity: UserEntity) = entity.let {
        if(it.id == null) {
            it.id = random.nextLong()
        }
        database.add(entity)
        it
    }

    override fun saveAll(entities: Set<UserEntity>) = entities.map {
        save(it)
    }.toSet()

    override fun findById(id: Long) = database.find { it.id == id }.optional()

    override fun findAll() = database.toSet()

    override fun delete(entity: UserEntity) {
        database.remove(entity)
    }
}