package com.untis.service.impl

import com.untis.database.repository.ServerSettingsRepository
import com.untis.model.ServerSettings
import com.untis.service.ServerSettingsService
import com.untis.service.mapping.createServerSettingsEntity
import com.untis.service.mapping.createServerSettingsModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseServerSettingsService @Autowired constructor(

    private val repository: ServerSettingsRepository

) : ServerSettingsService {

    override fun get(): ServerSettings {
        val entity = repository.findById(1).orElse(null)

        if (entity == null) {
            repository.save(createServerSettingsEntity(ServerSettings.Default))
            return ServerSettings.Default
        }
        if (entity.id != 1L) {
            repository.save(createServerSettingsEntity(ServerSettings.Default))
            return ServerSettings.Default
        }

        return createServerSettingsModel(entity)
    }

    override fun set(serverSettings: ServerSettings) {
        repository.save(createServerSettingsEntity(serverSettings))
    }

}