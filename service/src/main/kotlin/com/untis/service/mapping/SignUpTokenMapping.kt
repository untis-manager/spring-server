package com.untis.service.mapping

import com.untis.database.entity.SignupTokenEntity
import com.untis.model.SignUpToken
import com.untis.model.SignUpTokenUserData

fun createSignUpTokenModel(entity: SignupTokenEntity) = SignUpToken(
    id = entity.id,
    token = entity.token!!,
    timesUsed = entity.timesUsed!!,
    isSingleUse = !entity.isMultiUse!!,
    expirationDate = entity.expirationDate!!,
    userData = SignUpTokenUserData(
        email = entity.email,
        firstName = entity.firstName,
        lastName = entity.lastName,
        country = entity.country,
        city = entity.city,
        zipCode = entity.zipCode,
        street = entity.street,
        houseNumber = entity.houseNumber,
        gender = entity.gender?.let(::createGenderInfo),
        birthday = entity.birthday,
        groupIds = entity.groupIds!!.toSet(),
    )
)

fun createSignUpTokenEntity(model: SignUpToken) = SignupTokenEntity(
    id = model.id,
    token = model.token,
    timesUsed = model.timesUsed,
    isMultiUse = !model.isSingleUse,
    expirationDate = model.expirationDate,
    email = model.userData.email,
    firstName = model.userData.firstName,
    lastName = model.userData.lastName,
    country = model.userData.country,
    city = model.userData.city,
    zipCode = model.userData.zipCode,
    street = model.userData.street,
    houseNumber = model.userData.houseNumber,
    gender = model.userData.gender?.mapToString(),
    birthday = model.userData.birthday,
    groupIds = model.userData.groupIds.toList()
)