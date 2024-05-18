package com.untis.service.impl

import com.untis.database.repository.SignUpTokenRepository
import com.untis.model.SignUpToken
import com.untis.service.SignUpTokenService
import com.untis.service.mapping.createSignUpTokenEntity
import com.untis.service.mapping.createSignUpTokenModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
internal class DatabaseSignUpTokenService @Autowired constructor(
    private val repository: SignUpTokenRepository
) : SignUpTokenService {

    override fun getForToken(token: UUID) = repository
        .findByToken(token).orElse(null)
        ?.let(::createSignUpTokenModel)

    override fun incrementUsed(id: Long) = repository
        .findById(id).get()
        .apply { timesUsed = (timesUsed ?: 0) + 1 }
        .let(repository::save)
        .let(::createSignUpTokenModel)

    override fun isValid(token: UUID) = getForToken(token).let {
        it != null &&
                (it.timesUsed == 0 || !it.isSingleUse) &&
                it.expirationDate.isBefore(LocalDateTime.now())
    }

    override fun getAll() = repository
        .findAll()
        .map(::createSignUpTokenModel).toSet()

    override fun getById(id: Long) = repository
        .findById(id).get()
        .let(::createSignUpTokenModel)

    override fun getByIdAndUser(id: Long, user: Long): SignUpToken? {
        throw NotImplementedError("Sign up tokens are not bound to a specific user")
    }

    override fun getAllForUser(user: Long): List<SignUpToken> {
        throw NotImplementedError("Sign up tokens are not bound to a specific user")
    }

    override fun create(model: SignUpToken) =
        model.let(::createSignUpTokenEntity)
            .let(repository::save)
            .let(::createSignUpTokenModel)

    override fun delete(id: Long) = repository
        .findById(id).get()
        .apply(repository::delete)
        .let(::createSignUpTokenModel)

    override fun existsById(id: Long) = repository
        .existsById(id)

}