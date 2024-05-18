package com.untis.service.mapping

import com.untis.database.entity.GroupEntity
import com.untis.database.entity.UserEntity
import com.untis.model.AddressInfo
import com.untis.model.PermissionsBundle
import com.untis.model.User

internal fun createUserModel(entity: UserEntity, permissions: PermissionsBundle): User = User(
    id = entity.id!!,
    addressInfo = entity.extractAddressInfo(),
    firstName = entity.firstName!!,
    lastName = entity.lastName!!,
    email = entity.email!!,
    encodedPassword = entity.password!!,
    birthDate = entity.birthday!!,
    gender = createGenderInfo(entity.gender!!),
    permissions = permissions
)

internal fun createUserEntity(model: User, groupEntities: Set<GroupEntity>): UserEntity = UserEntity(
    id = model.id,
    firstName = model.firstName,
    lastName = model.lastName,
    email = model.email,
    password = model.encodedPassword,
    street = model.addressInfo.street,
    city = model.addressInfo.city,
    houseNumber = model.addressInfo.houseNumber,
    zipCode = model.addressInfo.zip,
    country = model.addressInfo.country,
    gender = model.gender.mapToString(),
    birthday = model.birthDate,
    groups = groupEntities.toMutableSet()
)

internal fun UserEntity.extractAddressInfo(): AddressInfo = AddressInfo(
    country = country!!,
    city = city!!,
    zip = zipCode!!,
    street = street!!,
    houseNumber = houseNumber!!,
)