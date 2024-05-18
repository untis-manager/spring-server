package com.untis.controller.rest.auth

import com.untis.controller.body.request.auth.LoginRequest
import com.untis.controller.body.response.TokensResponse
import com.untis.controller.util.unauthorized
import com.untis.model.SecurityTokenType
import com.untis.security.auth.JwtService
import com.untis.service.SecurityTokenService
import com.untis.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.token.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/login/")
class LoginAuthController @Autowired constructor(

    private val userService: UserService,

    private val passwordEncoder: PasswordEncoder,

    private val jwtService: JwtService,

    private val tokenService: SecurityTokenService

) {

    /**
     * Any client can sign up by submitting email and password.
     *
     * Returned is a jwt token.
     *
     * @param requestBody The email and password of the user
     * @return A new access and new refresh token
     */
    @PostMapping
    fun login(
        @RequestBody requestBody: LoginRequest
    ): ResponseEntity<TokensResponse> {
        val user = userService.getByEmail(requestBody.email) ?: return unauthorized()

        val valid = passwordEncoder.matches(requestBody.password, user.encodedPassword)

        if (!valid) return unauthorized()

        val accessToken = jwtService.createForUser(user)
        val refreshToken = tokenService.createForUser(user, SecurityTokenType.RefreshToken).token.toString()

        return ResponseEntity.ok(TokensResponse(accessToken, refreshToken))
    }

}