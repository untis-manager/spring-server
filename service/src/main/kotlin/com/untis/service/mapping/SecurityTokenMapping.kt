package com.untis.service.mapping

import com.untis.database.entity.SecurityTokenEntity
import com.untis.database.entity.UserEntity
import com.untis.model.SecurityToken
import com.untis.model.SecurityTokenType

internal fun createSecurityTokenModel(entity: SecurityTokenEntity) = SecurityToken(
    id = entity.id,
    token = entity.token!!,
    tokenType = createTokenType(entity.type!!),
    expirationDate = entity.expirationDate!!,
    used = entity.used!!,
    additionalInfo = entity.additionalInfo,
)

internal fun createSecurityTokenEntity(model: SecurityToken, user: UserEntity?) = SecurityTokenEntity(
    id = model.id,
    token = model.token,
    type = model.tokenType.name,
    expirationDate = model.expirationDate,
    used = model.used,
    user = user,
    additionalInfo = model.additionalInfo
)


internal fun createTokenType(str: String) = SecurityTokenType.valueOf(str)