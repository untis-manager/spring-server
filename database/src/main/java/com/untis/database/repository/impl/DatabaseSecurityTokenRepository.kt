package com.untis.database.repository.impl

import com.untis.database.entity.SecurityTokenEntity
import com.untis.database.repository.SecurityTokenRepository
import com.untis.database.repository.crud.SecurityTokenCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Component
internal class DatabaseSecurityTokenRepository @Autowired constructor(
    private val delegate: SecurityTokenCrudRepository
) : SecurityTokenRepository {

    override fun getByIdAndUser(id: Long, userId: Long) = delegate.getByIdAndUser(id, userId)

    override fun getAllForUser(userId: Long) = delegate.getAllForUser(userId)

    override fun findByToken(token: UUID) = delegate.findByToken(token)

    override fun findByTokenAndUser(token: UUID, userId: Long) = delegate.findByTokenAndUser(token, userId)

    override fun findByUserAndType(userId: Long, type: String) = delegate.findByUserAndType(userId, type)

    override fun save(entity: SecurityTokenEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<SecurityTokenEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: SecurityTokenEntity) = delegate.delete(entity)
}