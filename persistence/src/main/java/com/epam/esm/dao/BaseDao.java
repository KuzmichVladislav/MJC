package com.epam.esm.dao;

import com.epam.esm.entity.QueryParameter;

import java.util.List;
import java.util.Optional;

/**
 * The Interface BaseDao. A generic interface for persistence layer describes CRUD operations for working with database.
 *
 * @param <T> the generic entity type
 * @param <P> the query parameter
 */
public interface BaseDao<T, P extends QueryParameter> {

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
     * @param queryParameter the query parameter
     * @return the list of entity
     */
    List<T> findAll(P queryParameter);

    /**
     * Gets total number of items.
     *
     * @return the total number of items
     */
    long getTotalNumberOfItems();

    /**
     * Remove boolean.
     *
     * @param t the entity
     */
    void remove(T t);
}
