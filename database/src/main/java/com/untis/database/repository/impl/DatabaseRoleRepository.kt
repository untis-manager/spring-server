package com.untis.database.repository.impl

import com.untis.database.entity.RoleEntity
import com.untis.database.repository.RoleRepository
import com.untis.database.repository.crud.RoleCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
internal class DatabaseRoleRepository @Autowired constructor(
    private val delegate: RoleCrudRepository
) : RoleRepository {
    override fun save(entity: RoleEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<RoleEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: RoleEntity) = delegate.delete(entity)
}