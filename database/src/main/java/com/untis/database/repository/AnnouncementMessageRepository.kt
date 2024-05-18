package com.untis.database.repository

import com.untis.database.entity.AnnouncementMessageEntity
import com.untis.database.repository.base.SimpleRepository
import java.util.*

interface AnnouncementMessageRepository: SimpleRepository<AnnouncementMessageEntity> {

    fun getAllForGroups(groupIds: List<Long>): List<AnnouncementMessageEntity>

    fun getForAttachment(attachmentId: Long): Optional<AnnouncementMessageEntity>

    fun getAllForUser(userId: Long): List<AnnouncementMessageEntity>

}