package com.untis.model

import java.time.LocalDate

/**
 * Represents the data for a used that's stored in a [SignUpToken]
 *
 * Values with null can be chosen by user, values with a value are enforced to the user
 */
data class SignUpTokenUserData(

    /**
     * The email to enforce the user to
     */
    val email: String? = null,

    /**
     * The name to enforce the user to
     */
    val firstName: String? = null,

    /**
     * The last name to enforce the user to
     */
    val lastName: String? = null,

    /**
     * The street to enforce the user to
     */
    val street: String? = null,

    /**
     * The house number to enforce the user to
     */
    val houseNumber: String? = null,

    /**
     * The city to enforce the user to
     */
    val city: String? = null,

    /**
     * The zip code to enforce the user to
     */
    val zipCode: String? = null,

    /**
     * The country to enforce the user to
     */
    val country: String? = null,

    /**
     * The gender to enforce the user to
     */
    val gender: GenderInfo? = null,

    /**
     * The birthday to enforce the user to
     */
    val birthday: LocalDate? = null,

    /**
     * The role the user will have
     */
    val roleId: Long,

    /**
     * The groups the user will be joined in.
     */
    val groupIds: Set<Long> = emptySet()

)