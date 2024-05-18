package com.untis.service

import com.untis.model.SignUpToken
import com.untis.model.User
import java.util.*

/**
 * Service to perform operations on users that deal with authentication.
 */
interface UserSecurityService {

    /**
     * Creates a password-reset-token and emails it to the users email
     *
     * @param user The user
     * @param newPassword The new password to be persisted in the token
     */
    fun sendPasswordResetEmail(user: User, newPassword: String)


    /**
     * Changes a users password to the one associated with the token
     *
     * @param token The token the user passed
     * @return Whether the token was valid
     */
    fun changePassword(token: UUID): Boolean


    /**
     * Verifies the users credentials
     *
     * @param email The users email
     * @param password The users password
     *
     * @return The user when the credentials were valid, null otherwise
     */
    fun verifyUserCredentials(email: String, password: String): User?


    /**
     * Creates an email-change-token and emails it to the users old email
     *
     * @param newEmail The email the user wants to have verified
     * @param user The user
     */
    fun sendEmailChangeEmail(newEmail: String, user: User)


    /**
     * Changes the email related to the email-change-token
     *
     * @param token The email-change-token
     * @return Whether the token was valid
     */
    fun changeEmail(token: UUID): Boolean

    /**
     * Creates an email-validation-token and emails it to the given email
     *
     * @param email The email that was submitted by the user
     */
    fun sendEmailValidationEmail(email: String)

    /**
     * Checks whether an email-validation-token from a user is valid.
     *
     * @param token The token of the token
     * @param passedEmail The email to check it against
     * @return Whether the token is valid
     */
    fun isEmailValidationTokenValid(token: UUID, passedEmail: String): Boolean

}