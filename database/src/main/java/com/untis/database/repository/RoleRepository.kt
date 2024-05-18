package com.untis.database.repository

import com.untis.database.entity.GroupPermissions
import com.untis.database.repository.base.SimpleRepository
import org.springframework.stereotype.Repository

/**
 * Repository to access the entries of the [GroupPermissions] table
 */
@Repository
interface GroupPermissionsRepository : SimpleRepository<GroupPermissions>