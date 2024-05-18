package com.untis.controller.body.response

import com.untis.model.User
import com.untis.service.mapping.mapToString

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
