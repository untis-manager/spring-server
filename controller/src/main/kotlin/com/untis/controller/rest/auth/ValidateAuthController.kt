package com.untis.controller.rest.auth

import com.untis.common.toUUIDSafe
import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.VerifyEmailRequest
import com.untis.controller.body.request.VerifySecretRequest
import com.untis.controller.body.request.auth.VerifyEmailTokenRequest
import com.untis.controller.body.response.auth.VerifyResponse
import com.untis.model.SecurityTokenType
import com.untis.service.SecurityTokenService
import com.untis.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/auth/validate")
class ValidateAuthController @Autowired constructor(

    override val tokenService: SecurityTokenService,

    override val userService: UserService,

    @Value("\${untis.admin.secret}")
    private val secret: String

) : ControllerScope() {

    /**
     * Any client can submit an email-verification token and check whether it is valid or not
     *
     * @param requestBody The request body with the email and the token to check against
     * @return Whether the token is valid or not
     */
    @PostMapping("/email-token/")
    fun verifyEmailToken(
        @RequestBody requestBody: VerifyEmailTokenRequest
    ): VerifyResponse {
        val uuid = requestBody.token.toUUIDSafe()
        val token = uuid?.let(tokenService::getByToken)

        val isValid = token != null &&
                token.tokenType == SecurityTokenType.ValidateEmail &&
                !token.used &&
                token.expirationDate.isAfter(LocalDateTime.now()) &&
                token.additionalInfo == requestBody.email.trimIndent()

        return VerifyResponse(isValid)
    }

    /**
     * Any client can submit an email and check whether it is still free
     *
     * @param requestBody The request body with the email to check
     * @return Whether the email is still free or not
     */
    @PostMapping("/email/")
    fun verifyEmail(
        @RequestBody requestBody: VerifyEmailRequest
    ): VerifyResponse {
        val user = userService.getByEmail(requestBody.email)

        return VerifyResponse(user == null)
    }

    /**
     * Any client can submit a string and check whether it is a valid secret or not
     *
     * @return The request body with the secret
     * @return Whether the secret is valid or not
     */
    @PostMapping("/secret/")
    fun verifySecret(
        @RequestBody requestBody: VerifySecretRequest
    ): VerifyResponse = VerifyResponse(requestBody.secret == secret)

}