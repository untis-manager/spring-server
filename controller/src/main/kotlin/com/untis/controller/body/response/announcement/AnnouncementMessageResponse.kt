package com.untis.controller.body.response.announcement

import com.untis.model.AnnouncementMessage
import java.time.LocalDateTime

data class AnnouncementMessageResponse (

    val id: Long,

    val title: String,

    val content: String,

    val needsConfirmation: Boolean,

    val date: LocalDateTime

) {

    companion object {

        fun create(model: AnnouncementMessage) = AnnouncementMessageResponse(
            id = model.id!!,
            title = model.title,
            content = model.content,
            needsConfirmation = model.needsConfirmation,
            date = model.dateSent
        )

    }

}
