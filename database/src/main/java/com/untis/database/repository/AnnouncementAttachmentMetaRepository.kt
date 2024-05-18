package com.untis.database.repository

import com.untis.database.entity.AnnouncementAttachmentMetaEntity
import com.untis.database.repository.base.SimpleRepository

interface AnnouncementAttachmentMetaRepository: SimpleRepository<AnnouncementAttachmentMetaEntity> {

    fun getAttachmentsForAnnouncement(announcementId: Long): List<AnnouncementAttachmentMetaEntity>

}