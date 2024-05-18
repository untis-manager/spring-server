package com.untis.service.base

/**
 * Base of all service which contains methods all should share
 */
interface BaseService<Model> {

    /**
     * Gets all Models
     */
    fun getAll(): Set<Model>

    /**
     * Gets the Model by id
     */
    fun getById(id: Long): Model

    /**
     * Gets the model for the specific id.
     *
     * @param id The models' id. It is not automatically assumed that this id exists.
     * @param user The id of the user
     * @return The model, if it was found for the user, or null, if it was not found or it doesn't belong to the user
     */
    fun getByIdAndUser(id: Long, user: Long): Model?

    /**
     * Gets the all models belonging to that user
     *
     * @param user The id of the user
     * @return All models belonging to that user
     */
    fun getAllForUser(user: Long): List<Model>

    /**
     * Creates a new Model
     */
    fun create(model: Model): Model

    /**
     * Deletes the model by id
     */
    fun delete(id: Long): Model

    /**
     * Whether the model with the given id exists
     */
    fun existsById(id: Long): Boolean

}