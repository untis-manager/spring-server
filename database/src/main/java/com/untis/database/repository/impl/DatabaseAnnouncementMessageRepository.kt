package com.untis.database.repository.impl

import com.untis.database.entity.AnnouncementMessageEntity
import com.untis.database.repository.AnnouncementMessageRepository
import com.untis.database.repository.crud.AnnouncementMessageCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class DatabaseAnnouncementMessageRepository @Autowired constructor(
    val delegate: AnnouncementMessageCrudRepository
) : AnnouncementMessageRepository {

    override fun getAllForGroups(groupIds: List<Long>) = delegate.getAllForGroups(groupIds)

    override fun getForAttachment(attachmentId: Long) = delegate.getForAttachment(attachmentId)

    override fun getAllForUser(userId: Long) = delegate.getAllForUser(userId)

    override fun save(entity: AnnouncementMessageEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<AnnouncementMessageEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: AnnouncementMessageEntity) = delegate.delete(entity)

}