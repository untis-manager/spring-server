package com.untis.controller.body.response

import com.untis.model.Permission

data class PermissionResponse(

    val type: String,

    val stage: Int

) {

    companion object {

        fun create(permission: Permission) = PermissionResponse(permission.type, permission.stage)

    }

}
