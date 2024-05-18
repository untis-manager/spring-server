package com.untis.model

/**
 * Represents the metadata for one single attachment for a [AnnouncementMessage].
 * This does not store the actual attachment data.
 */
class AnnouncementAttachmentMeta(

    /**
     * The id
     */
    val id: Long?,

    /**
     * The filename of the file the user submitted, and the name that will be shown to the users.
     */
    val filename: String,

    /**
     * The size of the attachment file in bytes
     */
    val size: Long

)