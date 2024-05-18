package com.untis.database.repository.base

import java.util.Optional

/**
 * Base for all repository classes
 *
 * @param Entity The type that is persisted in this repository
 */
interface SimpleRepository<Entity> {

    /**
     * Saves the entity
     *
     * @param entity The entity to save
     * @return The changed entity
     */
    fun save(entity: Entity): Entity

    /**
     * Saves all the entities
     *
     * @param entities The entities to save
     * @return The changed entities
     */
    fun saveAll(entities: Set<Entity>): Set<Entity>

    /**
     * Finds an entity by a specified id
     *
     * @param id The id to search for
     * @return The entity, or empty
     */
    fun findById(id: Long): Optional<Entity>

    /**
     * Returns whether the entity with the given id exists
     *
     * @param id The id
     * @return Whether it exists or not
     */
    fun existsById(id: Long): Boolean = findById(id).isPresent

    /**
     * Gets all the entities
     *
     * @return All the persisted entities
     */
    fun findAll(): Set<Entity>

    /**
     * Deletes the entity
     *
     * @param entity The entity
     */
    fun delete(entity: Entity)

    /**
     * Gets all the entities for the ids
     *
     * @param ids The ids
     * @return The entities, or null if an entity for an id was not found
     */
    fun findAllById(ids: List<Long>): Set<Entity?> = ids.map { findById(it).orElse(null) }.toSet()

}