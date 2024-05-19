package com.untis.database.repository

import com.untis.database.entity.GroupPermissionsEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.stereotype.Repository

/**
 * Repository to access the entries of the [GroupPermissionsEntity] table
 */
@Repository
interface GroupPermissionsRepository : SimpleRepository<GroupPermissionsEntity>