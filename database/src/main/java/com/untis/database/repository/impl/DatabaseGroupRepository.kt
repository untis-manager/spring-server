package com.untis.database.repository.impl

import com.untis.database.entity.GroupEntity
import com.untis.database.entity.UserEntity
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.crud.GroupCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
internal class DatabaseGroupRepository @Autowired constructor(
    private val delegate: GroupCrudRepository
) : GroupRepository {

    override fun getGroupsForUser(userId: Long) = delegate.getGroupsForUser(userId)

    override fun getCoursesForGroup(groupId: Long) = delegate.getCoursesForGroup(groupId)

    override fun save(entity: GroupEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<GroupEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: GroupEntity) = delegate.delete(entity)
}