package com.untis.database.repository.crud

import com.untis.database.entity.GroupPermissions
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface GroupPermissionsCrudRepository : CrudRepository<GroupPermissions, Long>