package com.untis.database.repository.impl

import com.untis.database.entity.SignupTokenEntity
import com.untis.database.repository.SignUpTokenRepository
import com.untis.database.repository.crud.SignUpTokenCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class DatabaseSignUpTokenRepository @Autowired constructor(

    private val repo: SignUpTokenCrudRepository

) : SignUpTokenRepository {

    override fun findByToken(token: UUID) = repo.findByToken(token)

    override fun save(entity: SignupTokenEntity) = repo.save(entity)

    override fun saveAll(entities: Set<SignupTokenEntity>) = repo.saveAll(entities).toSet()

    override fun findById(id: Long) = repo.findById(id)

    override fun findAll() = repo.findAll().toSet()

    override fun delete(entity: SignupTokenEntity) = repo.delete(entity)

}