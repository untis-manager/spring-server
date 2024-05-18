package com.untis.controller.body.response

import com.untis.model.SignUpMode

data class SignUpModeResponse(

    // 'admin' or 'free' or 'token'
    val mode: String,

    /*
    If 'free'
     */
    val emailVerification: Boolean? = null,

    val defaultRoleId: Long? = null

) {

    companion object {

        fun create(model: SignUpMode) = when (model) {
            is SignUpMode.Free -> SignUpModeResponse(model.name, model.needEmailVerification, model.defaultRoleId)
            else -> SignUpModeResponse(model.name)
        }

    }

}
