package com.untis.service.mapping

import com.untis.database.entity.ServerSettingsEntity
import com.untis.model.ServerSettings
import com.untis.model.SignUpMode

fun createServerSettingsModel(entity: ServerSettingsEntity) = ServerSettings(
    signUpMode = entity.extractSignUpMode()
)

fun createServerSettingsEntity(model: ServerSettings) = ServerSettingsEntity(
    id = 1L,
    signupMode = model.signUpMode.name,
    signupFreeDefaultRoleId = (model.signUpMode as? SignUpMode.Free)?.defaultRoleId,
    signupFreeNeedsEmailVerification = (model.signUpMode as? SignUpMode.Free)?.needEmailVerification
)

private fun ServerSettingsEntity.extractSignUpMode(): SignUpMode = when (signupMode) {
    "free" -> SignUpMode.Free(
        defaultRoleId = signupFreeDefaultRoleId!!,
        needEmailVerification = signupFreeNeedsEmailVerification!!
    )

    "admin" -> SignUpMode.Admin
    "token" -> SignUpMode.Token
    else -> throw IllegalStateException("unknown signup mode: $signupMode")
}