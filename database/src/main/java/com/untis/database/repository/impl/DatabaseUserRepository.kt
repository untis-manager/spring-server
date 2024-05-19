package com.untis.database.repository.impl

import com.untis.database.entity.UserEntity
import com.untis.database.repository.UserRepository
import com.untis.database.repository.crud.UserCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class DatabaseUserRepository @Autowired constructor(
    private val delegate: UserCrudRepository
) : UserRepository {

    override fun getUserForSecurityToken(token: UUID) = delegate.getUserForSecurityToken(token)

    override fun getByEmail(email: String) = delegate.getByEmail(email)

    override fun getUsersInGroups(groupIds: List<Long>) = delegate.getUsersInGroups(groupIds)

    override fun save(entity: UserEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<UserEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: UserEntity) = delegate.delete(entity)
}