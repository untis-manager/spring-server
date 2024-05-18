package com.untis.database.repository

import com.untis.database.entity.RoleEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.stereotype.Repository

/**
 * Repository to access the entries of the [RoleEntity] table
 */
@Repository
interface RoleRepository : SimpleRepository<RoleEntity>