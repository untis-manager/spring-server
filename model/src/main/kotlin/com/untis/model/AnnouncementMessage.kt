package com.untis.model

import java.time.LocalDateTime

/**
 * Represents a singular message that can be sent to a broad amount of users.
 */
data class AnnouncementMessage(

    /**
     * The id
     */
    val id: Long?,

    /**
     * The title
     */
    val title: String,

    /**
     * The content
     */
    val content: String,

    /**
     * When the message was sent
     */
    val dateSent: LocalDateTime,

    /**
     * Whether the message can be confirmed by users
     */
    val needsConfirmation: Boolean

)