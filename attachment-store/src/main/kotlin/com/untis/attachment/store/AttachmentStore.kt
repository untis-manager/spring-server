package com.untis.attachment.store

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

/**
 * Interface to handle interactions with the persisted attachment files.
 *
 * Implementations assume that when passing an id, the attachment exists.
 */
interface AttachmentStore {

    /**
     * Saves the given file
     *
     * @param file The multipart file
     * @param id The id
     */
    fun save(file: MultipartFile, id: Long)

    /**
     * Saves the given file
     *
     * @param bytes The raw bytes
     * @param id The id
     */
    fun save(bytes: ByteArray, id: Long)

    /**
     * Deletes the given file irreversible
     *
     * @param id The id of the attachment to delete
     * @return Whether the file was deleted
     */
    fun delete(id: Long)

    /**
     * Whether an attachment with the given id exists
     *
     * @param id The id
     * @return Whether it exists
     */
    fun exists(id: Long): Boolean

    /**
     * Loads the attachment with [id] into a resource
     *
     * @param id The id
     * @return The resource with the attachment data
     */
    fun load(id: Long): Resource

}