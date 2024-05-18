package com.untis.service

import com.untis.model.ServerSettings

/**
 * Service that grants access to the globally used server settings
 */
interface ServerSettingsService {

    /**
     * Gets the stored server settings.
     * If none are stored, they get created using default values.
     */
    fun get(): ServerSettings

    /**
     * Updates the currently used server settings
     *
     * @param serverSettings The new settings
     */
    fun set(serverSettings: ServerSettings)

}