package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface OrderRepository for {@link Order}s.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find by user identifier.
     *
     * @param userId the user identifier
     * @return the list of orders
     */
    List<Order> findByUserId(long userId);
}
