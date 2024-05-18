package com.untis.controller.body.response.announcement

import com.untis.model.AnnouncementAttachmentMeta

data class AnnouncementAttachmentResponse (

    val id: Long,

    val name: String,

    val size: Long

) {

    companion object {

        fun create(model: AnnouncementAttachmentMeta) = AnnouncementAttachmentResponse(
            id = model.id!!,
            name = model.filename,
            size = model.size
        )

    }

}
