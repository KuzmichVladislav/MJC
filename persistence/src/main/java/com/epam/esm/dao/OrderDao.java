package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.entity.QueryParameter;

import java.util.List;

/**
 * The Interface OrderDao describes some query methods based on query object construction.
 */
public interface OrderDao extends BaseDao<Order, QueryParameter> {

    /**
     * Add gift certificate to order.
     *
     * @param orderCertificateDetails the order certificate details
     */
    void addGiftCertificateToOrder(OrderCertificateDetails orderCertificateDetails);

    /**
     * Find orders by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Order> findOrdersByUserId(long userId);
}
