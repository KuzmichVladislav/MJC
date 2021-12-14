package com.epam.esm.service;

import java.util.List;

/**
 * A generic interface for the Service layer. The interface
 * describes base business logic operations for Dao object.
 *
 * @param <T> the generic DTO object type
 *
 * @author Vladislav Kuzmich
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
    T findById(long id);

    /**
     * Find all DTO objects.
     *
     * @return the list of DTO objects
     */
    List<T> findAll();

    /**
     * Removes the by DTO object identifier.
     *
     * @param id the the DTO object identifier
     * @return true, if successful
     */
    boolean removeById(long id);
}
