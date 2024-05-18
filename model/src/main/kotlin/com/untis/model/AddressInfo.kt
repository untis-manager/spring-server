package com.untis.model

/**
 * Information about the address of a user
 */
data class AddressInfo (

    /**
     * Country of the user
     */
    val country: String,

    /**
     * City of the user
     */
    val city: String,

    /**
     * ZIP of the user
     */
    val zip: String,

    /**
     * Street of the user
     */
    val street: String,

    /**
     * House number of the user
     */
    val houseNumber: String,

)