package com.untis.controller.body.request.announcement

import com.fasterxml.jackson.annotation.JsonProperty

data class CreatedAnnouncementAttachmentRequest (

    @JsonProperty("name")
    val name: String

)
