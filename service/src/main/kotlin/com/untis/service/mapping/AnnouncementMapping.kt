package com.untis.service.mapping

import com.untis.database.entity.AnnouncementAttachmentMetaEntity
import com.untis.database.entity.AnnouncementMessageEntity
import com.untis.database.entity.GroupEntity
import com.untis.database.entity.UserEntity
import com.untis.model.AnnouncementAttachmentMeta
import com.untis.model.AnnouncementMessage

fun createAnnouncementMessageEntity(
    model: AnnouncementMessage,
    recipients: List<GroupEntity>,
    confirmedBy: List<UserEntity>,
    readBy: List<UserEntity>,
) = AnnouncementMessageEntity(
    id = model.id,
    title = model.title,
    content = model.content,
    dateSent = model.dateSent,
    needsConfirmation = model.needsConfirmation,
    recipients = recipients,
    confirmedBy = confirmedBy,
    readBy = readBy
)

fun createAnnouncementMessageModel(entity: AnnouncementMessageEntity) = AnnouncementMessage(
    id = entity.id,
    title = entity.title!!,
    content = entity.content!!,
    dateSent = entity.dateSent!!,
    needsConfirmation = entity.needsConfirmation!!
)

fun createAnnouncementAttachmentMetaEntity(model: AnnouncementAttachmentMeta, message: AnnouncementMessageEntity) = AnnouncementAttachmentMetaEntity(
    id = model.id,
    filename = model.filename,
    size = model.size,
    message = message
)

fun createAnnouncementAttachmentMetaModel(entity: AnnouncementAttachmentMetaEntity) = AnnouncementAttachmentMeta(
    id = entity.id,
    filename = entity.filename!!,
    size = entity.size!!
)