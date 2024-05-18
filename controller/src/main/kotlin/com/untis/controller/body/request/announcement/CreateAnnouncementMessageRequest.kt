package com.untis.controller.body.request.announcement

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAnnouncementMessageRequest (

    @JsonProperty("title")
    val title: String,

    @JsonProperty("content")
    val content: String,

    @JsonProperty("needsConfirmation")
    val needsConfirmation: Boolean,

    @JsonProperty("recipientGroups")
    val recipientGroups: List<Long>

)
