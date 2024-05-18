package com.untis.service.impl

import com.untis.database.repository.SecurityTokenRepository
import com.untis.database.repository.UserRepository
import com.untis.model.SecurityToken
import com.untis.model.SecurityTokenType
import com.untis.model.User
import com.untis.service.SecurityTokenService
import com.untis.service.mapping.createSecurityTokenEntity
import com.untis.service.mapping.createSecurityTokenModel
import com.untis.service.mapping.createUserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
internal class DatabaseSecurityTokenService @Autowired constructor(

    val securityTokenRepository: SecurityTokenRepository,

    val userRepository: UserRepository

) : SecurityTokenService {

    /*
    Retrieving data
     */


    override fun getAll() = securityTokenRepository
        .findAll()
        .map(::createSecurityTokenModel)
        .toSet()


    override fun getById(id: Long) = securityTokenRepository
        .findById(id).get()
        .let(::createSecurityTokenModel)

    override fun getByIdAndUser(id: Long, user: Long): SecurityToken? = securityTokenRepository
        .getByIdAndUser(id, user).orElse(null)
        .let(::createSecurityTokenModel)

    override fun getAllForUser(user: Long): List<SecurityToken> = securityTokenRepository
        .getAllForUser(user)
        .map(::createSecurityTokenModel)


    override fun existsById(id: Long) = securityTokenRepository
        .existsById(id)


    override fun existsByToken(token: UUID) = securityTokenRepository
        .findByToken(token)
        .isPresent

    override fun getByToken(token: UUID) = securityTokenRepository
        .findByToken(token).orElse(null)
        ?.let(::createSecurityTokenModel)

    override fun getByTokenAndUser(token: UUID, userId: Long): SecurityToken? =
        securityTokenRepository
            .findByTokenAndUser(token, userId)
            .orElse(null)
            ?.let(::createSecurityTokenModel)


    override fun getUserFor(token: UUID) = userRepository
        .getUserForSecurityToken(token).orElse(null)
        ?.let(::createUserModel)


    override fun create(model: SecurityToken): SecurityToken {
        throw NotImplementedError("Use the method which accepts a token and a user")
    }

    override fun createForUser(user: User, type: SecurityTokenType, additionalInfo: String?) = SecurityToken(
        id = null,
        token = UUID.randomUUID(),
        tokenType = type,
        expirationDate = LocalDateTime.now()
            .plus(type.expirationDuration.toMillis(), ChronoUnit.MILLIS),
        used = false,
        additionalInfo = additionalInfo
    ).let {
        createSecurityTokenEntity(it, userRepository.findById(user.id!!).get())
    }.let(securityTokenRepository::save)
        .let(::createSecurityTokenModel)

    override fun create(type: SecurityTokenType, additionalInfo: String?) = SecurityToken(
        id = null,
        token = UUID.randomUUID(),
        tokenType = type,
        expirationDate = LocalDateTime.now()
            .plus(type.expirationDuration.toMillis(), ChronoUnit.MILLIS),
        used = false,
        additionalInfo = additionalInfo
    ).let {
        createSecurityTokenEntity(it, null)
    }.let(securityTokenRepository::save)
        .let(::createSecurityTokenModel)

    override fun delete(id: Long) = securityTokenRepository
        .findById(id).get()
        .apply(securityTokenRepository::delete)
        .let(::createSecurityTokenModel)


    override fun markAsUsed(id: Long) = securityTokenRepository
        .findById(id).get()
        .let { token ->
            token.used = true
            token
        }
        .let(securityTokenRepository::save)
        .let(::createSecurityTokenModel)

    override fun getByUserAndType(user: User, type: SecurityTokenType) = securityTokenRepository
        .findByUserAndType(user.id!!, type.name)
        .map(::createSecurityTokenModel)
        .toSet()
}