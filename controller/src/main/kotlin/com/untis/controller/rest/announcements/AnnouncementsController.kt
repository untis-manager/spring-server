package com.untis.controller.rest.announcements

import com.untis.controller.base.ControllerScope
import com.untis.controller.body.response.announcement.AnnouncementAttachmentResponse
import com.untis.controller.validating.validateAnnouncementMessageExists
import com.untis.model.AnnouncementAttachmentMeta
import com.untis.service.AnnouncementAttachmentService
import com.untis.service.AnnouncementMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/announcements/")
class AnnouncementsController @Autowired constructor(

    override val announcementMessageService: AnnouncementMessageService,

    override val announcementAttachmentService: AnnouncementAttachmentService

) : ControllerScope() {

    @PostMapping("{id}/attachments/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addAttachment(
        @PathVariable id: Long,
        @RequestParam("attachmentFile") attachmentFile: MultipartFile
    ): AnnouncementAttachmentResponse {
        validateAnnouncementMessageExists(id)

        val model = AnnouncementAttachmentMeta(
            id = null,
            filename = attachmentFile.originalFilename ?: attachmentFile.name,
            size = attachmentFile.size
        ).let {
            announcementAttachmentService.create(it, id, attachmentFile)
        }



        return AnnouncementAttachmentResponse.create(model)
    }

}