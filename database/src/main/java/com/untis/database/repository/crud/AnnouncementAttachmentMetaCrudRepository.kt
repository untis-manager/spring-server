package com.untis.database.repository.crud

import com.untis.database.entity.AnnouncementAttachmentMetaEntity
import com.untis.database.entity.AnnouncementMessageEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface AnnouncementAttachmentMetaCrudRepository : CrudRepository<AnnouncementAttachmentMetaEntity, Long> {

    @Query("""
        SELECT attachment
        FROM announcement_attachments attachment
        WHERE attachment.message.id = :messageId
    """)
    fun getAttachmentsForMessage(messageId: Long): List<AnnouncementAttachmentMetaEntity>

}