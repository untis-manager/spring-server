package com.untis.controller.body.response

import com.untis.model.AddressInfo

data class AddressResponse (

    val country: String,

    val city: String,

    val zip: String,

    val street: String,

    val houseNumber: String,

) {

    companion object {

        fun create(addressInfo: AddressInfo) = addressInfo.run { AddressResponse(country, city, zip, street, houseNumber) }

    }

}
