package com.untis.mail

import com.untis.model.SecurityToken
import com.untis.model.User

/**
 * The service that allows to send specified mails to certain recipients
 */
interface MailService {

    /**
     * Sends the password-reset-token [token] to the users email
     *
     * @param user The user to send it to
     * @param token The token to send to the email
     */
    fun sendPasswordResetToken(user: User, token: SecurityToken)

    /**
     * Sends the email-reset-token [token] to the users current
     *
     * @param user The user to send it to
     * @param token The token to send the user
     */
    fun sendEmailResetToken(user: User, token: SecurityToken)

    /**
     * Sends the email-confirm-token [token] to the [email]
     *
     * @param email The email to send it to
     * @param token The token
     */
    fun sendEmailConfirmToken(email: String, token: SecurityToken)

}