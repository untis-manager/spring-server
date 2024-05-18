package com.untis.model

import java.time.LocalDateTime
import java.util.UUID

/**
 * Model that represents a token to assist and guide a user doing sign up
 */
data class SignUpToken(

    /**
     * The id of the token
     */
    val id: Long? = null,

    /**
     * The actual token
     */
    val token: UUID,

    /**
     * The amount of times this token has been used
     */
    val timesUsed: Int,

    /**
     * Whether this token can be used multiple times
     */
    val isSingleUse: Boolean,

    /**
     * The date until when the token can be used
     */
    val expirationDate: LocalDateTime,

    /**
     * The actual data to enforce to the user.
     * Fields that are null can be freely chosen by the user.
     */
    val userData: SignUpTokenUserData

)