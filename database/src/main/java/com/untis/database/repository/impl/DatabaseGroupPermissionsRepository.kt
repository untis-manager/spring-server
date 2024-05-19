package com.untis.database.repository.impl

import com.untis.database.entity.GroupPermissionsEntity
import com.untis.database.repository.GroupPermissionsRepository
import com.untis.database.repository.crud.GroupPermissionsCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class DatabaseGroupPermissionsRepository @Autowired constructor(
    private val delegate: GroupPermissionsCrudRepository
) : GroupPermissionsRepository {
    override fun save(entity: GroupPermissionsEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<GroupPermissionsEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: GroupPermissionsEntity) = delegate.delete(entity)
}