package com.untis.service.mapping

import com.untis.database.entity.GroupEntity
import com.untis.model.Group

internal fun createGroupModel(entity: GroupEntity) = Group(
    id = entity.id!!,
    name = entity.name!!
)

internal fun createGroupEntity(model: Group) =
    GroupEntity(
        id = model.id,
        name = model.name,
    )