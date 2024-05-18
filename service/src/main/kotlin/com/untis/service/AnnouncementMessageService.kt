package com.untis.service

import com.untis.model.AnnouncementMessage
import com.untis.model.Group
import com.untis.model.User
import com.untis.service.base.BaseService
import java.time.LocalDateTime

/**
 * Service to perform operations on announcement messages.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface AnnouncementMessageService : BaseService<AnnouncementMessage> {

    /**
     * Creates a new message
     *
     * @param message The message
     * @param recipientGroups The groups the message is sent to
     * @return The created message
     */
    fun create(
        message: AnnouncementMessage,
        recipientGroups: List<Long>
    ): AnnouncementMessage

    /**
     * Gets all messages in a time interval
     *
     * @param from The lower bound of the interval
     * @param to The upper bound of the interval
     * @return All messages in the interval
     */
    fun getAll(
        from: LocalDateTime = LocalDateTime.now(),
        to: LocalDateTime = LocalDateTime.now()
    ): Set<AnnouncementMessage>

    /**
     * Gets the groups associated with a specific message
     *
     * @param id: The id of the message
     * @return: The groups
     */
    fun getGroupsFor(id: Long): List<Group>

    /**
     * Gets the messages associated with groups
     *
     * @param groupId: The ids of the groups
     * @return: The messages
     */
    fun getForGroups(vararg groupId: Long): Set<AnnouncementMessage>

    /**
     * Updates the users that have read the message
     *
     * @param id The id of the message
     * @param users The ids of the users
     * @return The new ids of all users that have now read the message
     */
    fun updateReadBy(id: Long, users: List<Long>)

    /**
     * Gets all users that have read a specific message
     *
     * @param id The id of the message
     * @return All users that have read the message
     */
    fun getReadBy(id: Long): List<User>

    /**
     * Updates the users that have confirmed the message
     *
     * @param id The id of the message
     * @param users The ids of the users
     * @return The new ids of all users that have now confirmed the message
     */
    fun updateConfirmedBy(id: Long, users: List<Long>)

    /**
     * Gets all users that have confirmed a specific message
     *
     * @param id The id of the message
     * @return All users that have confirmed the message
     */
    fun getConfirmedBy(id: Long): List<User>

    /**
     * Updates a specific message
     *
     * @param message The updated message
     * @return The updated message
     */
    fun update(message: AnnouncementMessage): AnnouncementMessage

    /**
     * Gets the message for a specific attachment
     *
     * @param attachmentId The id of the attachment
     * @return The announcement message
     */
    fun getForAttachment(attachmentId: Long): AnnouncementMessage

    /**
     * Returns whether the user has access to the message, i.e. if any of the groups the user is part of is in the recipients of the message.
     *
     * Returns false even if the user has [RolePermissions.canViewAllData]
     *
     * @param userId The id of the user
     * @param messageId The id of the message
     * @return Whether the user has access to the message
     */
    fun userHasAccessTo(userId: Long, messageId: Long): Boolean

}