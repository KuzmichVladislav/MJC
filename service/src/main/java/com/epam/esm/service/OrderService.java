package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The Interface OrderService.
 * A interface to define all required methods for order DTO object.
 */
public interface OrderService {

    /**
     * Adds the order DTO object.
     *
     * @param orderDto the order DTO object
     * @return the order DTO object
     */
    OrderDto add(OrderDto orderDto);

    /**
     * Find by order DTO object identifier.
     *
     * @param id the order DTO object identifier
     * @return the order DTO object
     */
    OrderDto findById(long id);

    /**
     * Find orders by order DTO object identifier.
     *
     * @param userId the order DTO object identifier
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

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<OrderDto> findAll(Pageable pageable);
}
