package com.untis.database.repository.crud

import com.untis.database.entity.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface UserCrudRepository : CrudRepository<UserEntity, Long> {

    @Query("""
        SELECT u
        FROM users u
        WHERE u.role.id = :roleId
    """)
    fun getAllWithRole(roleId: Long)

    @Query("""
        SELECT u
        from users u
        INNER JOIN security_token s ON s.user.id = u.id
        WHERE s.token = :token
    """)
    fun getUserForSecurityToken(token: UUID): Optional<UserEntity>

    fun getByEmail(email: String): Optional<UserEntity>

    @Query("""
        SELECT u
        FROM users u
        WHERE u.role.id = :roleId
    """)
    fun getByRole(roleId: Long): List<UserEntity>

    @Query("""
        SELECT u
        FROM users u
        INNER JOIN u.groups g
        WHERE g.id = :groupId
    """)
    fun getUsersInGroup(groupId: Long): List<UserEntity>

}