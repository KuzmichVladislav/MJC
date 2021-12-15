package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The Interface BaseDao. A generic interface for persistence layer describes CRUD operations for working with database.
 *
 * @param <T> the generic entity type
 */
public interface BaseDao<T> {

    /**
     * Adds the entity.
     *
     * @param t the entity
     * @return the entity
     */
    T add(T t);

    /**
     * Find by entity identifier.
     *
     * @param id the entity identifier
     * @return the optional of entity
     */
    Optional<T> findById(long id);

    /**
     * Find all entities.
     *
     * @return the list of entity
     */
    List<T> findAll();

    /**
     * Removes the by entity identifier.
     *
     * @param id the the entity identifier
     * @return true, if successful
     */
    boolean removeById(long id);
}
