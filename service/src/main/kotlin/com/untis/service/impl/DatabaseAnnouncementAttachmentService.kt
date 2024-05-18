package com.untis.service.impl

import com.untis.attachment.store.AttachmentStore
import com.untis.database.repository.AnnouncementAttachmentMetaRepository
import com.untis.database.repository.AnnouncementMessageRepository
import com.untis.model.AnnouncementAttachmentMeta
import com.untis.service.AnnouncementAttachmentService
import com.untis.service.mapping.createAnnouncementAttachmentMetaEntity
import com.untis.service.mapping.createAnnouncementAttachmentMetaModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
internal class DatabaseAnnouncementAttachmentService @Autowired constructor(

    val attachmentRepository: AnnouncementAttachmentMetaRepository,

    val messageRepository: AnnouncementMessageRepository,

    val attachmentStore: AttachmentStore

) : AnnouncementAttachmentService {

    override fun getAll() = attachmentRepository
        .findAll()
        .map(::createAnnouncementAttachmentMetaModel)
        .toSet()

    override fun getById(id: Long) = attachmentRepository
        .findById(id).get()
        .let(::createAnnouncementAttachmentMetaModel)

    override fun getByIdAndUser(id: Long, user: Long): AnnouncementAttachmentMeta? {
        throw NotImplementedError("Announcement attachments are not bound to users")
    }

    override fun getAllForUser(user: Long): List<AnnouncementAttachmentMeta> {
        throw NotImplementedError("Announcement attachments are not bound to users")
    }

    override fun create(
        model: AnnouncementAttachmentMeta,
        messageId: Long,
        file: MultipartFile
    ): AnnouncementAttachmentMeta {
        val message = messageRepository.findById(messageId).get()

        val attachmentMeta = createAnnouncementAttachmentMetaEntity(model, message)
            .let(attachmentRepository::save)
            .let(::createAnnouncementAttachmentMetaModel)

        attachmentStore.save(file, attachmentMeta.id!!)

        return attachmentMeta
    }

    override fun create(model: AnnouncementAttachmentMeta): AnnouncementAttachmentMeta {
        throw NotImplementedError("Use the method with the message id")
    }

    override fun getForMessage(messageId: Long) = attachmentRepository
        .getAttachmentsForAnnouncement(messageId)
        .map(::createAnnouncementAttachmentMetaModel)

    override fun getResource(id: Long) = attachmentStore
        .load(id)

    override fun delete(id: Long): AnnouncementAttachmentMeta {
        attachmentStore.delete(id)

        return attachmentRepository
            .findById(id).get()
            .apply(attachmentRepository::delete)
            .let(::createAnnouncementAttachmentMetaModel)
    }

    override fun existsById(id: Long) = attachmentRepository
        .existsById(id)

}