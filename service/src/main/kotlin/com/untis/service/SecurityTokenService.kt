package com.untis.service

import com.untis.model.SecurityToken
import com.untis.model.SecurityTokenType
import com.untis.model.User
import com.untis.service.base.BaseService
import java.util.*

/**
 * Service to perform operations on course instance infos.
 *
 * Methods of this service assume that a given id exists in the database. Passing non-existing id's leads to undefined behaviour
 */
interface SecurityTokenService : BaseService<SecurityToken> {

    /**
     * Checks whether a token with the given token exists
     *
     * @param token The token
     * @return Whether it exists or not
     */
    fun existsByToken(token: UUID): Boolean

    /**
     * Returns the user that the token is associated with
     *
     * @param token The token
     * @return The user of the token or null if it was not found
     */
    fun getUserFor(token: UUID): User?

    /**
     * Gets the token by token id
     *
     * @param token The token
     * @return The token, or null if it doesn't exist
     */
    fun getByToken(token: UUID): SecurityToken?

    /**
     * Gets the token by id
     *
     * @param token The token, not automatically assumed to exist
     * @param userId The id of the user
     * @return The token, or null if it doesn't exists or belong to the user
     */
    fun getByTokenAndUser(token: UUID, userId: Long): SecurityToken?

    /**
     * Creates a new security token
     *
     * @param user The user to generate it for
     * @param type The type of token
     *
     * @return The created token
     */
    fun createForUser(user: User, type: SecurityTokenType, additionalInfo: String? = null): SecurityToken

    /**
     * Creates a new security token
     *
     * @param type The type of token
     * @param additionalInfo The optional additional info
     * @return The created token
     */
    fun create(type: SecurityTokenType, additionalInfo: String? = null): SecurityToken

    /**
     * Marks a security token as used
     *
     * @param id The id of the token
     *
     * @return The marked token
     */
    fun markAsUsed(id: Long): SecurityToken

    /**
     * Gets all the tokens for a specific type and user
     *
     * @param user The user
     * @param type The type
     * @return All matching tokens
     */
    fun getByUserAndType(user: User, type: SecurityTokenType): Set<SecurityToken>

}