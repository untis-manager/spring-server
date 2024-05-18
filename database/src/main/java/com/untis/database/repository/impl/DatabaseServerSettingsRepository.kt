package com.untis.database.repository.impl

import com.untis.database.entity.ServerSettingsEntity
import com.untis.database.repository.ServerSettingsRepository
import com.untis.database.repository.crud.ServerSettingsCrudRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class DatabaseServerSettingsRepository @Autowired constructor(

    private val repo: ServerSettingsCrudRepository

) : ServerSettingsRepository {

    override fun save(entity: ServerSettingsEntity) = repo.save(entity)

    override fun saveAll(entities: Set<ServerSettingsEntity>) = repo.saveAll(entities).toSet()

    override fun findById(id: Long) = repo.findById(id)

    override fun findAll() = repo.findAll().toSet()

    override fun delete(entity: ServerSettingsEntity) = repo.delete(entity)


}