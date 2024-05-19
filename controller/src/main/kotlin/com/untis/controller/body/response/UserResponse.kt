package com.untis.controller.body.response

import com.untis.controller.body.parameter.UserRequestModeParameter
import com.untis.model.Permission
import com.untis.model.User
import com.untis.service.mapping.mapToString
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class FullUserResponse(

    val id: Long,

    val email: String,

    val firstName: String,

    val lastName: String,

    val address: AddressResponse,

    val gender: String,

    val birthDay: String

) {

    companion object {

        fun create(user: User) = user.run {
            FullUserResponse(
                id!!,
                email,
                firstName,
                lastName,
                AddressResponse.create(user.addressInfo),
                gender.mapToString(),
                birthDate.toString()
            )
        }

    }

}

data class PartialUserResponse(

    val id: Long,

    val firstName: String,

    val lastName: String

) {

    companion object {

        fun create(user: User) = user.run {
            PartialUserResponse(
                id!!,
                firstName,
                lastName
            )
        }

    }
}

fun createUserResponse(
    user: User,
    mode: UserRequestModeParameter,
    userPerms: Permission.Users
): ResponseEntity<Any> = when (mode) {
    UserRequestModeParameter.Partial -> {
        if(Permission.Users.Partial.matches(userPerms))
            ResponseEntity.ok(PartialUserResponse.create(user))
        else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
    UserRequestModeParameter.Full -> {
        if(Permission.Users.Full.matches(userPerms))
            ResponseEntity.ok(FullUserResponse.create(user))
        else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
}
