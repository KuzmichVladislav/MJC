package com.epam.esm.entity.audit;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class OrderListener {

    @PrePersist
    public void createdOn(Order order) {
        order.setPurchaseTime(LocalDateTime.now());
    }
}
