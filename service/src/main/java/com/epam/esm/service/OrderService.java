package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.QueryParameterDto;

import java.util.List;

/**
 * The Interface OrderService.
 * A interface to define all required methods for order DTO object.
 */
public interface OrderService extends BaseService<OrderDto, QueryParameterDto> {

    /**
     * Find orders by user identifier.
     *
     * @param userId the user identifier
     * @return the list of order DTO objects
     */
    List<OrderDto> findOrdersByUserId(long userId);

    /**
     * Removes the by order DTO object identifier.
     *
     * @param id the order DTO object identifier
     * @return the order DTO object
     */
    OrderDto removeById(long id);
}
