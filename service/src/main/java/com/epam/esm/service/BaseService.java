package com.epam.esm.service;

import com.epam.esm.dto.QueryParameterDto;
import org.springframework.hateoas.PagedModel;

/**
 * A generic interface for the Service layer. The interface
 * describes base business logic operations for DTO objects.
 *
 * @param <T> the generic DTO object type
 * @param <P> the query parameter
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
     * @param queryParameterDto the query parameter dto
     * @return the page
     */
    PagedModel<T> findAll(P queryParameterDto);

    /**
     * Removes the by DTO object identifier.
     *
     * @param id the the DTO object identifier
     */
    void removeById(long id);
}
