package com.untis.service

import com.untis.model.AnnouncementAttachmentMeta
import com.untis.service.base.BaseService
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

/**
 * Service to perform operations on announcement messages.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface AnnouncementAttachmentService : BaseService<AnnouncementAttachmentMeta> {

    /**
     * Creates a new attachment for a specific message
     *
     * @param model The attachment
     * @param messageId The id of the message
     * @return The new attachment
     */
    fun create(model: AnnouncementAttachmentMeta, messageId: Long, file: MultipartFile): AnnouncementAttachmentMeta

    /**
     * Gets the attachments from a specific message
     *
     * @param messageId The id of the message
     * @return All attachments metas from that message
     */
    fun getForMessage(messageId: Long): List<AnnouncementAttachmentMeta>

    /**
     * Gets the file contents of a specific attachment as a resource
     *
     * @param id The id of the attachment
     * @return The file content, as a resource
     */
    fun getResource(id: Long): Resource

}