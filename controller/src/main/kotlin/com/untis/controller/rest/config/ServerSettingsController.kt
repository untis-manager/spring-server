package com.untis.controller.rest.config

import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.settings.UpdateSignUpModeRequest
import com.untis.controller.body.response.SignUpModeResponse
import com.untis.controller.body.response.config.OrganizationNameResponse
import com.untis.controller.util.internalError
import com.untis.controller.util.ok
import com.untis.controller.validating.validateGroupExists
import com.untis.model.SignUpMode
import com.untis.model.exception.RequestException
import com.untis.service.GroupService
import com.untis.service.ServerSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File

/**
 * Access to the server settings
 */
@RestController
@RequestMapping("/server-config")
class ServerSettingsController @Autowired constructor(

    val serverSettingsService: ServerSettingsService,

    override val groupService: GroupService,

    @Value("\${untis.icon}")
    private val iconPath: String

) : ControllerScope() {

    /**
     * Endpoint that returns information about the currently active sign up mode
     *
     * @return The sign-up mode
     */
    @GetMapping("/signup-mode/")
    fun signUpMode(): SignUpModeResponse = serverSettingsService
        .get().signUpMode
        .let(SignUpModeResponse::create)

    /**
     * Endpoint that returns the name of the organization
     *
     * @return The name
     */
    @GetMapping("/organization-name/")
    fun organizationName(): OrganizationNameResponse = serverSettingsService
        .get().organizationName
        .let(OrganizationNameResponse::create)

    /**
     * Endpoint that returns the icon set by an admin as a multipart file.
     *
     * @return The file as a [Resource]
     */
    @GetMapping("/organization-icon/")
    fun organizationIcon(): ResponseEntity<Resource> {
        val iconFile = File(iconPath)

        return if(iconFile.exists()) {
            ok(FileSystemResource(iconFile))
        } else {
            internalError()
        }
    }

    /**
     * Endpoint that changes the currently active sign up mode
     *
     * @param requestBody The information about the new sign-up mode
     * @return The sign-up mode
     */
    @PatchMapping("/signup-mode/")
    fun updateSignUpMode(
        @RequestBody requestBody: UpdateSignUpModeRequest
    ): SignUpModeResponse {
        requestBody.validate()

        val signUpMode = when (requestBody.mode) {
            SignUpMode.Names.Admin -> SignUpMode.Admin
            SignUpMode.Names.Token -> SignUpMode.Token
            SignUpMode.Names.Free -> SignUpMode.Free(requestBody.defaultGroupId!!, requestBody.emailVerification!!)
            else -> throw IllegalStateException()
        }

        val serverSettings = serverSettingsService.get()
        val updated = serverSettings.copy(
            signUpMode = signUpMode
        )

        serverSettingsService.set(updated)
        return SignUpModeResponse.create(signUpMode)
    }

    private fun UpdateSignUpModeRequest.validate() {
        if (mode !in SignUpMode.Names.all()) throw RequestException.ParamsBad("Unknown sign-up-mode: '$mode'")

        if (mode == SignUpMode.Names.Free) {
            if (emailVerification == null || defaultGroupId == null) throw RequestException.ParamsBad("For sign up mode '$mode', 'emailVerification' and 'defaultRoleId' must be passed.")
            validateGroupExists(defaultGroupId)
        }
    }

}