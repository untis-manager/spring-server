package com.untis.database.repository

import com.untis.database.entity.SecurityTokenEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

/**
 * Repository to access the Security Token table
 */
interface SecurityTokenRepository: SimpleRepository<SecurityTokenEntity> {

    fun getByIdAndUser(id: Long, userId: Long): Optional<SecurityTokenEntity>

    fun getAllForUser(userId: Long): List<SecurityTokenEntity>

    fun findByToken(token: UUID): Optional<SecurityTokenEntity>

    fun findByTokenAndUser(token: UUID, userId: Long): Optional<SecurityTokenEntity>

    fun findByUserAndType(userId: Long, type: String): List<SecurityTokenEntity>

}