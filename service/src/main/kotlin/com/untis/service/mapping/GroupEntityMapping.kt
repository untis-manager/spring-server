package com.untis.service.mapping

import com.untis.database.entity.GroupEntity
import com.untis.database.entity.GroupPermissionsEntity
import com.untis.model.Group
import com.untis.model.PermissionsBundle

internal fun createGroupModel(entity: GroupEntity) = Group(
    id = entity.id!!,
    name = entity.name!!,
    permissions = createPermissionBundleModel(entity.permissions!!)
)

internal fun createGroupEntity(model: Group) =
    GroupEntity(
        id = model.id,
        name = model.name,
    )