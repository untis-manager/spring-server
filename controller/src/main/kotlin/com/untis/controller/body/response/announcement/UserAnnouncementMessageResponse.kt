package com.untis.controller.body.response.announcement

import com.untis.model.AnnouncementMessage
import java.time.LocalDateTime

data class UserAnnouncementMessageResponse (

    val id: Long,

    val title: String,

    val content: String,

    val needsConfirmation: Boolean,

    val date: LocalDateTime,

    val confirmedByUser: Boolean,

    val readByUser: Boolean

) {

    companion object {

        fun create(model: AnnouncementMessage, confirmedByUser: Boolean, readByUser: Boolean) = UserAnnouncementMessageResponse(
            id = model.id!!,
            title = model.title,
            content = model.content,
            needsConfirmation = model.needsConfirmation,
            date = model.dateSent,
            confirmedByUser = confirmedByUser,
            readByUser = readByUser
        )

    }

}
