package com.untis.controller.rest.auth

import com.untis.common.toUUID
import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.auth.LogoutRequest
import com.untis.controller.body.request.auth.RefreshRequest
import com.untis.controller.body.response.TokensResponse
import com.untis.controller.util.unauthorized
import com.untis.controller.validating.validateUUIDString
import com.untis.model.SecurityTokenType
import com.untis.security.auth.JwtService
import com.untis.service.SecurityTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/refresh/")
class RefreshAuthController @Autowired constructor(

    override val tokenService: SecurityTokenService,

    private val jwtService: JwtService

) : ControllerScope() {

    /**
     * Any client can request a new access token using a valid refresh token.
     *
     * The refresh token that is used will be deleted.
     *
     * @param requestBody The old refresh token
     */
    @PostMapping
    fun refresh(
        @RequestBody requestBody: RefreshRequest
    ): ResponseEntity<TokensResponse> {
        validateUUIDString(requestBody.refreshToken)

        val refreshToken = tokenService.getByToken(requestBody.refreshToken.toUUID()) ?: return unauthorized()
        if (refreshToken.tokenType != SecurityTokenType.RefreshToken) return unauthorized()
        val user = tokenService.getUserFor(refreshToken.token) ?: return unauthorized()

        tokenService.delete(refreshToken.id!!)

        val newRefreshToken = tokenService.createForUser(user, SecurityTokenType.RefreshToken)
        val newAccessToken = jwtService.createForUser(user)

        return ResponseEntity.ok(TokensResponse(newAccessToken, newRefreshToken.token.toString()))
    }

}