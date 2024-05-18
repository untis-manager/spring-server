package com.untis.model

import java.time.Duration

/**
 * Types of security tokens
 */
enum class SecurityTokenType(

    /**
     * The amount of time that a token lasts
     */
    val expirationDuration: Duration

) {

    /**
     * Token for resetting a password
     */
    ResetPassword(Duration.ofMinutes(5)),

    /**
     * Token for resetting an email
     */
    ResetEmail(Duration.ofMinutes(5)),

    /**
     * Token for validating an email when creating an account
     */
    ValidateEmail(Duration.ofHours(1)),

    /**
     * Token for refreshing a users access token
     */
    RefreshToken(Duration.ofDays(90)),

}