package com.epam.esm.service;

import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;

/**
 * A generic interface for the Service layer. The interface
 * describes base business logic operations for DTO objects.
 *
 * @param <T> the generic DTO object type
 */

public interface BaseService<T, P extends QueryParameterDto> {

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
    PageWrapper<T> findAll(P queryParameterDto);

    /**
     * Removes the by DTO object identifier.
     *
     * @param id the the DTO object identifier
     * @return true, if successful
     */
    boolean removeById(long id);
}
