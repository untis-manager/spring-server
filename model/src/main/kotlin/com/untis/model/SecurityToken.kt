package com.untis.model

import java.time.LocalDateTime
import java.util.UUID

/**
 * Model for a security token used for 2FA
 */
data class SecurityToken(

    /**
     * The id
     */
    val id: Long? = null,

    /**
     * The token itself, used as a password
     */
    val token: UUID,

    /**
     * The purpose of the token
     */
    val tokenType: SecurityTokenType,

    /**
     * The [LocalDateTime] on which the token expires
     */
    val expirationDate: LocalDateTime,

    /**
     * Whether the token has been used or not
     */
    val used: Boolean,

    /**
     * Additional info, like for email-change-token the new email
     */
    val additionalInfo: String? = null

)
