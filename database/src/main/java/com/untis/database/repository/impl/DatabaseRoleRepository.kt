package com.untis.database.repository.impl

import com.untis.database.entity.GroupPermissions
import com.untis.database.repository.GroupPermissionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class DatabaseGroupPermissionsRepository @Autowired constructor(
    private val delegate: GroupPermissionsRepository
) : GroupPermissionsRepository {
    override fun save(entity: GroupPermissions) = delegate.save(entity)

    override fun saveAll(entities: Set<GroupPermissions>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: GroupPermissions) = delegate.delete(entity)
}