package com.untis.service

import com.untis.model.SignUpToken
import com.untis.service.base.BaseService
import java.util.*

/**
 * Service to perform operations on sign-up-tokens.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface SignUpTokenService : BaseService<SignUpToken> {

    /**
     * Gets the token that corresponds to the uuid
     *
     * @param token The token
     */
    fun getForToken(token: UUID): SignUpToken?

    /**
     * Increments the uses of the token with the [id]
     *
     * @param id The id of the token to update
     */
    fun incrementUsed(id: Long): SignUpToken

    /**
     * Checks whether the token with [token] is valid
     *
     * @param token The token
     * @return Whether it is still valid to use
     */
    fun isValid(token: UUID): Boolean

}