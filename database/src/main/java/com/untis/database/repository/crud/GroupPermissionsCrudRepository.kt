package com.untis.database.repository.crud

import com.untis.database.entity.GroupPermissionsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface GroupPermissionsCrudRepository : CrudRepository<GroupPermissionsEntity, Long>