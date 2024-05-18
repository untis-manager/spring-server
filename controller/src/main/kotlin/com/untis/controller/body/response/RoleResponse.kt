package com.untis.controller.body.response

data class RoleResponse(

    val id: Long,

    val name: String,

    val permissions: UserPermissionsResponse

) {

    companion object {

        fun create(role: Role) = role.run {
            RoleResponse(id!!, name, UserPermissionsResponse.create(role.permissions))
        }

    }

}
