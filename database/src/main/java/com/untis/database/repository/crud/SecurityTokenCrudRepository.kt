package com.untis.database.repository.crud

import com.untis.database.entity.SecurityTokenEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
internal interface SecurityTokenCrudRepository : CrudRepository<SecurityTokenEntity, Long> {

    @Query("""
        SELECT s
        FROM security_token s
        WHERE s.id = :id AND s.user.id = :userId
    """)
    fun getByIdAndUser(id: Long, userId: Long): Optional<SecurityTokenEntity>

    @Query("""
        SELECT s
        FROM security_token s
        WHERE s.user.id = :userId
    """)
    fun getAllForUser(userId: Long): List<SecurityTokenEntity>

    @Query("""
        SELECT s
        FROM security_token s
        WHERE s.token = :token
    """)
    fun findByToken(token: UUID): Optional<SecurityTokenEntity>

    @Query("""
        SELECT s
        FROM security_token s
        WHERE s.token = :token AND s.user.id = :userId
    """)
    fun findByTokenAndUser(token: UUID, userId: Long): Optional<SecurityTokenEntity>

    @Query("""
        SELECT s
        FROM security_token s
        WHERE s.type = :type AND s.user.id = :userId
    """)
    fun findByUserAndType(userId: Long, type: String): List<SecurityTokenEntity>

}