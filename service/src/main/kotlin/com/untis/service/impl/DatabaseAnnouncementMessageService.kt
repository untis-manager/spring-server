package com.untis.service.impl

import com.untis.attachment.store.AttachmentStore
import com.untis.database.repository.AnnouncementAttachmentMetaRepository
import com.untis.database.repository.AnnouncementMessageRepository
import com.untis.database.repository.GroupRepository
import com.untis.database.repository.UserRepository
import com.untis.model.AnnouncementMessage
import com.untis.model.Group
import com.untis.service.AnnouncementMessageService
import com.untis.service.mapping.createAnnouncementMessageEntity
import com.untis.service.mapping.createAnnouncementMessageModel
import com.untis.service.mapping.createGroupModel
import com.untis.service.mapping.createUserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
internal class DatabaseAnnouncementMessageService @Autowired constructor(

    val messageRepository: AnnouncementMessageRepository,

    val groupRepository: GroupRepository,

    val attachmentRepository: AnnouncementAttachmentMetaRepository,

    val attachmentStore: AttachmentStore,

    val userRepository: UserRepository

) : AnnouncementMessageService {

    override fun getAll(from: LocalDateTime, to: LocalDateTime) = messageRepository
        .findAll()
        .filter { it.dateSent!!.isAfter(from) && it.dateSent!!.isBefore(to) }
        .map(::createAnnouncementMessageModel).toSet()

    override fun getAll() = messageRepository
        .findAll()
        .map(::createAnnouncementMessageModel).toSet()

    override fun getGroupsFor(id: Long): List<Group> =
        messageRepository.findById(id).get()
            .recipients!!
            .map(::createGroupModel)

    override fun getForGroups(vararg groupId: Long) = messageRepository
        .getAllForGroups(groupId.toList())
        .map(::createAnnouncementMessageModel)
        .toSet()

    override fun updateReadBy(id: Long, users: List<Long>) {
        val entity = messageRepository.findById(id).get()

        val userEntities = users.map(userRepository::findById).map { it.get() }
        entity.readBy = userEntities
        messageRepository.save(entity)
    }

    override fun updateConfirmedBy(id: Long, users: List<Long>) {
        val entity = messageRepository.findById(id).get()

        val userEntities = users.map(userRepository::findById).map { it.get() }
        entity.confirmedBy = userEntities
        messageRepository.save(entity)
    }

    override fun getReadBy(id: Long) = messageRepository
        .findById(id).get()
        .readBy!!
        .map(::createUserModel)

    override fun getConfirmedBy(id: Long) = messageRepository
        .findById(id).get()
        .confirmedBy!!
        .map(::createUserModel)

    override fun update(message: AnnouncementMessage): AnnouncementMessage {
        if (!existsById(message.id!!)) throw IllegalArgumentException("Message with '${message.id}' not found")

        val readBy = messageRepository.findById(message.id!!).get().readBy!!
        val confirmedBy = messageRepository.findById(message.id!!).get().confirmedBy!!
        val recipients = messageRepository.findById(message.id!!).get().recipients!!

        val entity = createAnnouncementMessageEntity(message, recipients, readBy, confirmedBy)

        return messageRepository
            .save(entity)
            .let(::createAnnouncementMessageModel)
    }

    override fun getForAttachment(attachmentId: Long) = messageRepository
        .getForAttachment(attachmentId).get()
        .let(::createAnnouncementMessageModel)

    override fun userHasAccessTo(userId: Long, messageId: Long): Boolean {
        val userGroups = userRepository
            .findById(userId).get()
            .groups!!

        val messageGroups = messageRepository
            .findById(messageId).get()
            .recipients!!

        return userGroups.any { it in messageGroups }
    }

    override fun getById(id: Long) = messageRepository
        .findById(id).get()
        .let(::createAnnouncementMessageModel)

    override fun getByIdAndUser(id: Long, user: Long): AnnouncementMessage? {
        val message = messageRepository.findById(id).orElse(null)
        val messageGroups = message.recipients?.map { it.id } ?: emptyList()

        return if (user in messageGroups) createAnnouncementMessageModel(message)
        else null
    }

    override fun getAllForUser(user: Long) = messageRepository
        .getAllForUser(user)
        .map(::createAnnouncementMessageModel)

    override fun delete(id: Long) = messageRepository
        .findById(id).get()
        .apply(messageRepository::delete)
        .let(::createAnnouncementMessageModel)

    override fun existsById(id: Long) = messageRepository.existsById(id)

    override fun create(
        message: AnnouncementMessage,
        recipientGroups: List<Long>
    ): AnnouncementMessage {
        val groups = recipientGroups.map { groupRepository.findById(it).get() }

        val entity = createAnnouncementMessageEntity(message, groups, emptyList(), emptyList())
            .let(messageRepository::save)

        return createAnnouncementMessageModel(entity)
    }

    override fun create(model: AnnouncementMessage): AnnouncementMessage {
        throw NotImplementedError("Use the method with recipients!")
    }
}