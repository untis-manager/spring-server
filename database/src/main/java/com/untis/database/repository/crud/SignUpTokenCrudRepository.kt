package com.untis.database.repository.crud

import com.untis.database.entity.SignupTokenEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface SignUpTokenCrudRepository : CrudRepository<SignupTokenEntity, Long> {

    @Query("""
        SELECT s
        from signup_token s
        WHERE s.token = :token
    """)
    fun findByToken(token: UUID): Optional<SignupTokenEntity>

}