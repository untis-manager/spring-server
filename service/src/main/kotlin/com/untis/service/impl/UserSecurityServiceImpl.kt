package com.untis.service.impl

import com.untis.mail.MailService
import com.untis.model.Permission
import com.untis.model.SecurityTokenType
import com.untis.model.User
import com.untis.service.GroupService
import com.untis.service.SecurityTokenService
import com.untis.service.UserSecurityService
import com.untis.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

/**
 * Service to perform operations on users.
 */
@Service
internal class UserSecurityServiceImpl @Autowired constructor(

    val userService: UserService,

    val groupService: GroupService,

    val tokenService: SecurityTokenService,

    val mailService: MailService,

    val passwordEncoder: PasswordEncoder

) : UserSecurityService {


    override fun sendPasswordResetEmail(user: User, newPassword: String) {
        val encodedNewPassword = passwordEncoder.encode(newPassword)

        val newToken = tokenService.createForUser(user, SecurityTokenType.ResetPassword, encodedNewPassword)

        mailService.sendPasswordResetToken(user, newToken)
    }


    override fun changePassword(token: UUID): Boolean {
        val secToken = tokenService.getByToken(token)
        val user = secToken?.token?.let(tokenService::getUserFor)

        val isValid = secToken != null &&
                secToken.expirationDate.isAfter(LocalDateTime.now()) &&
                !secToken.used &&
                secToken.tokenType == SecurityTokenType.ResetPassword &&
                secToken.additionalInfo != null &&
                user != null &&
                user.permissions.profile.satisfies(Permission.Profile.Edit)

        if (!isValid) return false

        userService.update(user!!.copy(encodedPassword = secToken.additionalInfo!!))

        tokenService.markAsUsed(secToken.id!!)

        return true
    }


    override fun verifyUserCredentials(email: String, password: String): User? = userService
        .getByEmail(email)
        .let { user ->
            val valid = passwordEncoder.matches(password, user?.encodedPassword ?: "")

            if (!valid || user == null) null
            else user
        }


    override fun sendEmailChangeEmail(newEmail: String, user: User) {
        val newToken = tokenService.createForUser(user, SecurityTokenType.ResetEmail, newEmail)
        mailService.sendEmailResetToken(user, newToken)
    }


    override fun changeEmail(token: UUID): Boolean {
        val secToken = tokenService.getByToken(token) ?: return false
        val user = secToken.token.let(tokenService::getUserFor)

        val isValid = secToken.expirationDate.isAfter(LocalDateTime.now()) &&
                !secToken.used &&
                secToken.additionalInfo != null &&
                secToken.tokenType == SecurityTokenType.ResetEmail &&
                user != null &&
                user.permissions.profile.satisfies(Permission.Profile.Edit)

        if (!isValid) return false

        userService.update(user!!.copy(email = secToken.additionalInfo!!))

        tokenService.markAsUsed(secToken.id!!)

        return true
    }

    override fun sendEmailValidationEmail(email: String) {
        val secToken = tokenService.create(SecurityTokenType.ValidateEmail, email)

        mailService.sendEmailConfirmToken(email, secToken)
    }

    override fun isEmailValidationTokenValid(token: UUID, passedEmail: String): Boolean {
        val secToken = tokenService.getByToken(token) ?: return false

        val isValid = !secToken.used &&
                secToken.tokenType == SecurityTokenType.ValidateEmail &&
                secToken.expirationDate.isAfter(LocalDateTime.now()) &&
                secToken.additionalInfo == passedEmail

        return isValid
    }

}