package com.untis.database.repository.impl

import com.untis.database.entity.GroupEntity
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.crud.GroupCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class DatabaseGroupRepository @Autowired constructor(
    private val delegate: GroupCrudRepository
) : GroupRepository {

    override fun getGroupsForUser(userId: Long) = delegate.getGroupsForUser(userId)

    override fun getParentGroups(id: Long): List<GroupEntity> {
        var group = findById(id).get()
        val parents = mutableListOf(group)
        while (group.parentGroup != null) {
            parents.add(group.parentGroup!!)
            group = group.parentGroup!!
        }

        return parents
    }

    override fun getParentGroups(ids: List<Long>): List<GroupEntity> {
        val eachParents = ids.map(::getParentGroups)
        return eachParents.flatten().distinctBy { it.id }
    }

    override fun getChildrenGroups(id: Long): List<GroupEntity> {
        val group = findById(id).get()
        return if (group.directChildrenGroups!!.isEmpty()) emptyList()
        else group.directChildrenGroups!!.map { getChildrenGroups(it.id!!) }.flatten().distinctBy { it.id }
    }

    override fun getCoursesForGroup(groupId: Long) = delegate.getCoursesForGroup(groupId)

    override fun save(entity: GroupEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<GroupEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: GroupEntity) = delegate.delete(entity)
}