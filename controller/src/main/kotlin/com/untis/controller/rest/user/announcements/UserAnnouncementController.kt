package com.untis.controller.rest.user.announcements

import com.untis.controller.base.ControllerScope
import com.untis.controller.body.parameter.MessageMarkParameter
import com.untis.controller.body.parameter.UserRequestModeParameter
import com.untis.controller.body.response.announcement.AnnouncementAttachmentResponse
import com.untis.controller.body.response.announcement.UserAnnouncementMessageResponse
import com.untis.controller.body.response.createUserResponse
import com.untis.controller.body.response.group.GroupResponse
import com.untis.controller.util.conflict
import com.untis.controller.util.ok
import com.untis.controller.validating.validateAnnouncementMessageExists
import com.untis.model.User
import com.untis.model.exception.RequestException
import com.untis.service.AnnouncementAttachmentService
import com.untis.service.AnnouncementMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/announcements/")
class UserAnnouncementController @Autowired constructor(

    override val announcementMessageService: AnnouncementMessageService,

    override val announcementAttachmentService: AnnouncementAttachmentService

) : ControllerScope() {

    /**
     * Returns all the announcement messages of the user
     *
     * @param user The authenticated user
     * @return The messages
     */
    @Suppress("DuplicatedCode")
    @GetMapping
    fun getAll(
        @AuthenticationPrincipal user: User
    ): List<UserAnnouncementMessageResponse> = announcementMessageService
        .getAllForUser(user.id!!)
        .map { message ->
            val confirmed = announcementMessageService.getConfirmedBy(message.id!!).map { it.id!! }
            val read = announcementMessageService.getReadBy(message.id!!).map { it.id!! }

            UserAnnouncementMessageResponse.create(
                model = message,
                confirmedByUser = user.id!! in confirmed,
                readByUser = user.id!! in read
            )
        }

    /**
     * Returns the attachment message by id
     *
     * @param user The authenticated user
     * @param id The path variable id of the message
     * @return The announcement message
     */
    @Suppress("DuplicatedCode")
    @GetMapping("{id}/")
    fun getById(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long
    ): UserAnnouncementMessageResponse {
        validateAnnouncementMessageExists(id, user.id!!)

        return announcementMessageService
            .getById(id)
            .let {
                val confirmed = announcementMessageService.getConfirmedBy(it.id!!).map { it.id!! }
                val read = announcementMessageService.getReadBy(it.id!!).map { it.id!! }

                UserAnnouncementMessageResponse.create(
                    model = it,
                    confirmedByUser = user.id!! in confirmed,
                    readByUser = user.id!! in read
                )
            }
    }

    /**
     * Marks a message according to the [status]
     *
     * @param user The authenticated user
     * @param id The path variable id of the message
     * @param status What the message should be marked as
     * @return The announcement message
     */
    @Suppress("DuplicatedCode")
    @PutMapping("{id}/")
    fun mark(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long,
        @RequestParam status: MessageMarkParameter
    ): ResponseEntity<UserAnnouncementMessageResponse> {
        validateAnnouncementMessageExists(id, user.id!!)

        val msg = announcementMessageService
            .getById(id)

        when (status) {
            MessageMarkParameter.Read -> {
                val readBy = announcementMessageService.getReadBy(id).map { it.id!! }
                if (user.id!! in readBy) return conflict()
                announcementMessageService.updateReadBy(id, readBy + user.id!!)
            }

            MessageMarkParameter.Confirmed -> {
                val confirmedBy = announcementMessageService.getConfirmedBy(id).map { it.id!! }
                if (user.id!! in confirmedBy || !msg.needsConfirmation) return conflict()
                announcementMessageService.updateConfirmedBy(id, confirmedBy + user.id!!)
            }
        }

        val response = announcementMessageService
            .getById(id).let {
                val confirmed = announcementMessageService.getConfirmedBy(it.id!!).map { it.id!! }
                val read = announcementMessageService.getReadBy(it.id!!).map { it.id!! }

                UserAnnouncementMessageResponse.create(
                    model = it,
                    confirmedByUser = user.id!! in confirmed,
                    readByUser = user.id!! in read
                )
            }

        return ok(response)
    }

    /**
     * Returns the groups a specific announcement message was sent to
     *
     * @param user The authenticated user
     * @param id The path variable id of the message
     * @return The groups it was sent to
     */
    @GetMapping("{id}/groups/")
    fun getMessageGroups(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long
    ): List<GroupResponse> {
        validateAnnouncementMessageExists(id, user.id!!)

        return announcementMessageService
            .getRecipientGroups(id)
            .map(GroupResponse::create)
    }

    /**
     * Returns the author that a specific announcement was sent by
     *
     * @param user The authenticated user
     * @param id The path variable id of the message
     * @return The author it was sent to
     */
    @GetMapping("{id}/author/")
    fun getMessageAuthor(
        @AuthenticationPrincipal user: User,
        @RequestParam mode: UserRequestModeParameter,
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        validateAnnouncementMessageExists(id, user.id!!)

        val author = announcementMessageService.getAuthorFor(id)

        val r = createUserResponse(
            user = author,
            mode = mode,
            userPerms = user.permissions.users
        )
        return r
    }

    /**
     * Returns the attachment metas for a specific message
     *
     * @param user The authenticated user
     * @param id The path variable id of the message
     * @return The attachment metas for the message
     */
    @GetMapping("{id}/attachments/")
    fun getMessageAttachmentMetas(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long
    ): List<AnnouncementAttachmentResponse> {
        validateAnnouncementMessageExists(id, user.id!!)

        return announcementAttachmentService
            .getForMessage(id)
            .map(AnnouncementAttachmentResponse::create)
    }

    /**
     * Returns a specific attachment meta for a specific message
     *
     * @param user The authenticated user
     * @param messageId The path variable id of the message
     * @param attachmentId The path variable id of the attachment
     * @return The attachment meta for the message
     */
    @Suppress("DuplicatedCode")
    @GetMapping("{messageId}/attachments/{attachmentId}/")
    fun getMessageAttachmentMeta(
        @AuthenticationPrincipal user: User,
        @PathVariable messageId: Long,
        @PathVariable attachmentId: Long
    ): AnnouncementAttachmentResponse {
        validateAnnouncementMessageExists(messageId, user.id!!)

        val attachmentMetas = announcementAttachmentService
            .getForMessage(messageId)

        val actual = attachmentMetas.firstOrNull { it.id == attachmentId }

        if (actual == null) throw RequestException.IdNotFound(attachmentId.toString(), "announcement attachment")
        return AnnouncementAttachmentResponse.create(actual)
    }

    /**
     * Returns the file content of a specific attachment for a specific message
     *
     * @param user The authenticated user
     * @param messageId The path variable id of the message
     * @param attachmentId The path variable id of the attachment
     * @return The attachment meta for the message
     */
    @Suppress("DuplicatedCode")
    @GetMapping("{messageId}/attachments/{attachmentId}/file/")
    fun getMessageAttachmentFile(
        @AuthenticationPrincipal user: User,
        @PathVariable messageId: Long,
        @PathVariable attachmentId: Long
    ): ResponseEntity<Resource> {
        validateAnnouncementMessageExists(messageId, user.id!!)

        val attachmentMetas = announcementAttachmentService
            .getForMessage(messageId)

        val actual = attachmentMetas.firstOrNull { it.id == attachmentId }

        if (actual == null) throw RequestException.IdNotFound(attachmentId.toString(), "announcement attachment")

        val resource = announcementAttachmentService.getResource(attachmentId)

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
            .body(resource)
    }

}