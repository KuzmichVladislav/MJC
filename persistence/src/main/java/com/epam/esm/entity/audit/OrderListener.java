package com.epam.esm.entity.audit;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

/**
 * The type Order listener serves to track changes in the order table in the database.
 */
public class OrderListener {

    /**
     * Populates the create and purchase time field when adding an order entry to the database.
     *
     * @param order the order entity
     */
    @PrePersist
    public void createdOn(Order order) {
        order.setPurchaseTime(LocalDateTime.now());
    }
}
