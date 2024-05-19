package com.untis.database.repository

import com.untis.database.entity.UserEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository to access the entries of the [UserEntity] table
 */
@Repository
interface UserRepository : SimpleRepository<UserEntity> {

    fun getUserForSecurityToken(token: UUID): Optional<UserEntity>

    fun getByEmail(email: String): Optional<UserEntity>

    fun getUsersInGroups(groupIds: List<Long>): List<UserEntity>

}