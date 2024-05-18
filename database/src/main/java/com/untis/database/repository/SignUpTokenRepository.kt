package com.untis.database.repository

import com.untis.database.entity.SignupTokenEntity
import com.untis.database.repository.base.SimpleRepository
import java.util.*

/**
 * Repository to access the records of the sign-up-token table
 */
interface SignUpTokenRepository : SimpleRepository<SignupTokenEntity> {

    fun findByToken(token: UUID): Optional<SignupTokenEntity>

}