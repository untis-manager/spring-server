package com.untis.controller.body.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TokensResponse(

    val accessToken: String,

    val refreshToken: String

)
