package com.untis.controller.rest.auth

import com.untis.common.toUUID
import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.auth.LogoutRequest
import com.untis.controller.validating.validateTokenExists
import com.untis.controller.validating.validateUUIDString
import com.untis.model.SecurityTokenType
import com.untis.model.User
import com.untis.service.SecurityTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/logout/")
class LogoutAuthController @Autowired constructor(

    override val tokenService: SecurityTokenService

): ControllerScope() {

    /**
     * Any authenticated client can request to delete the specified refresh token
     *
     * @param user The user
     * @param requestBody The specified refresh token
     */
    @PostMapping
    fun logout(
        @AuthenticationPrincipal user: User,
        @RequestBody requestBody: LogoutRequest
    ) {
        validateUUIDString(requestBody.token)
        validateTokenExists(requestBody.token.toUUID())

        tokenService
            .getByToken(requestBody.token.toUUID())!!
            .id!!
            .let(tokenService::delete)
    }

    /**
     * Any authenticated client can request to delete all stored refresh tokens.
     *
     * @param user The user
     */
    @PostMapping("/all/")
    fun logout(
        @AuthenticationPrincipal user: User,
    ) {
        tokenService
            .getByUserAndType(user, SecurityTokenType.RefreshToken)
            .forEach { tokenService.delete(it.id!!) }
    }

}