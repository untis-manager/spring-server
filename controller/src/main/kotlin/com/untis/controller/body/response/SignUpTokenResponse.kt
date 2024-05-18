package com.untis.controller.body.response

import com.untis.model.SignUpToken
import com.untis.service.mapping.mapToString

data class SignUpTokenResponse(

    val email: String? = null,

    val firstName: String? = null,

    val lastName: String? = null,

    val street: String? = null,

    val houseNumber: String? = null,

    val city: String? = null,

    val zipCode: String? = null,

    val country: String? = null,

    val gender: String? = null,

    val birthday: String? = null

) {

    companion object {

        fun create(token: SignUpToken) = token.userData.run {
            SignUpTokenResponse(
                email,
                firstName,
                lastName,
                street,
                houseNumber,
                city,
                zipCode,
                country,
                gender?.mapToString(),
                birthday?.toString()
            )
        }

    }

}
