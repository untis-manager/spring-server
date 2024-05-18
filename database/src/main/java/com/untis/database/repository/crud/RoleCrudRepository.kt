package com.untis.database.repository.crud

import com.untis.database.entity.RoleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
internal interface RoleCrudRepository : CrudRepository<RoleEntity, Long>