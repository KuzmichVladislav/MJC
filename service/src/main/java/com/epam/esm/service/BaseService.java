package com.epam.esm.service;

import java.util.List;

/**
 * A generic interface for the Service layer. The interface
 * describes base business logic operations for DTO objects.
 *
 * @param <T> the generic DTO object type
 */

public interface BaseService<T> {

    /**
     * Adds the DTO object.
     *
     * @param t the DTO object
     * @return the DTO object
     */
    T add(T t);

    /**
     * Find by DTO object identifier.
     *
     * @param id the DTO object identifier
     * @return the DTO object
     */
    T findById(String id);

    /**
     * Find all DTO objects.
     *
     * @return the list of DTO objects
     */
    List<T> findAll(int page, int size);

    /**
     * Removes the by DTO object identifier.
     *
     * @param id the the DTO object identifier
     * @return true, if successful
     */
    boolean removeById(String id);
}
