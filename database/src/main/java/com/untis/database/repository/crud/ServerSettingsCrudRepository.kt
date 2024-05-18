package com.untis.database.repository.crud

import com.untis.database.entity.ServerSettingsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface ServerSettingsCrudRepository : CrudRepository<ServerSettingsEntity, Long>