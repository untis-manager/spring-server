package com.untis.controller.rest.user

import com.untis.common.toUUID
import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.personal.EmailChangeRequest
import com.untis.controller.body.request.personal.PasswordChangeRequest
import com.untis.controller.validating.validateEmail
import com.untis.controller.validating.validateUUIDString
import com.untis.model.Permission
import com.untis.service.SecurityTokenService
import com.untis.service.UserSecurityService
import com.untis.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * The rest endpoint that handles interactions of a user with their account and related security measures,
 *      such as resetting the email, changing the password or deleting the account
 */
@RestController("/user/")
class PersonalSecurityController @Autowired constructor(

    override val tokenService: SecurityTokenService,

    override val userService: UserService,

    val userSecurityService: UserSecurityService

) : ControllerScope() {

    /**
     * Endpoint that lets a non-authenticated user
     *
     * 1. Request to send a password-reset-token to a specific email OR
     * 2. Submit a password-reset-token to change the password
     *
     * Only allows users to change their password that have the 'CAN_EDIT_OWN_PROFILE' Authority.
     * The new password is stored in the password-reset-token to be used when it is confirmed
     *
     * @param requestBody The request body for the request
     */
    @PatchMapping("/password/")
    fun resetPassword(
        @RequestBody requestBody: PasswordChangeRequest
    ): ResponseEntity<Any> {
        val isRequestEmail =
            requestBody.email != null && requestBody.newPassword != null && requestBody.token == null
        val isUseToken =
            requestBody.token != null && requestBody.newPassword == null && requestBody.email == null

        if (isRequestEmail) {
            validateEmail(requestBody.email!!)

            val user = userService.getByEmail(requestBody.email)

            if (user != null) {
                userSecurityService.sendPasswordResetEmail(user, requestBody.newPassword!!)
            }

            return ResponseEntity.accepted().build()
        } else if (isUseToken) {
            validateUUIDString(requestBody.token!!)

            val successful = userSecurityService.changePassword(requestBody.token.toUUID())

            return if (successful) ResponseEntity.ok().build()
            else ResponseEntity.badRequest()
                .body("The token '${requestBody.token}' was not found/is expired/has been used before.")
        }

        return ResponseEntity.badRequest()
            .body("Either 'email and 'newPassword' or only 'token' needs to be passed.")
    }

    /**
     * Endpoint that lets a non-authenticated user
     *
     * 1. Request to send an email-change-token to a new email address ( needs verification with old email and password)
     * 2. Submit an email-change-token to commit the change
     *
     * @param requestBody The request body for the request
     */
    @PatchMapping("/email/")
    fun changeEmail(
        @RequestBody(required = false) requestBody: EmailChangeRequest
    ): ResponseEntity<Any> {
        val isRequestEmail =
            requestBody.newEmail != null && requestBody.oldEmail != null && requestBody.password != null && requestBody.token == null
        val isUseToken =
            requestBody.newEmail == null && requestBody.oldEmail == null && requestBody.password == null && requestBody.token != null

        if (isRequestEmail) {
            validateEmail(requestBody.newEmail!!)

            val user = userSecurityService.verifyUserCredentials(requestBody.oldEmail!!, requestBody.password!!)

            if (user == null) {
                return ResponseEntity(HttpStatus.UNAUTHORIZED)
            } else if (!user.permissions.profile.matches(Permission.Profile.Edit)) {
                return ResponseEntity(HttpStatus.FORBIDDEN)
            }

            userSecurityService.sendEmailChangeEmail(requestBody.newEmail, user)

            return ResponseEntity.accepted().build()
        } else if (isUseToken) {
            validateUUIDString(requestBody.token!!)

            val successful = userSecurityService.changeEmail(requestBody.token.toUUID())

            return if (successful) ResponseEntity.ok().build()
            else ResponseEntity.badRequest()
                .body("The token '${requestBody.token}' was not found/is expired/has been used before.")
        }

        return ResponseEntity.badRequest()
            .body("Either only 'oldEmail', 'newEmail' and 'password' or only 'token' need to be passed.")
    }
}