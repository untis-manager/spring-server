package com.untis.database.repository.impl

import com.untis.database.entity.AnnouncementAttachmentMetaEntity
import com.untis.database.entity.AnnouncementMessageEntity
import com.untis.database.repository.AnnouncementAttachmentMetaRepository
import com.untis.database.repository.AnnouncementMessageRepository
import com.untis.database.repository.crud.AnnouncementAttachmentMetaCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class DatabaseAnnouncementAttachmentMetaRepository @Autowired constructor(
    val delegate: AnnouncementAttachmentMetaCrudRepository
) : AnnouncementAttachmentMetaRepository {

    override fun getAttachmentsForAnnouncement(announcementId: Long) = delegate.getAttachmentsForMessage(announcementId)

    override fun save(entity: AnnouncementAttachmentMetaEntity) = delegate.save(entity)

    override fun saveAll(entities: Set<AnnouncementAttachmentMetaEntity>) = delegate.saveAll(entities).toSet()

    override fun findById(id: Long) = delegate.findById(id)

    override fun findAll() = delegate.findAll().toSet()

    override fun delete(entity: AnnouncementAttachmentMetaEntity) = delegate.delete(entity)

}