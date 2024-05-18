package com.untis.database.repository

import com.untis.database.entity.ServerSettingsEntity
import com.untis.database.repository.base.SimpleRepository

/**
 * Repository to access the singleton instance of the server settings
 */
interface ServerSettingsRepository: SimpleRepository<ServerSettingsEntity>