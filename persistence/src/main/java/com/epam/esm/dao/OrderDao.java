package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {

    void addGiftCertificateToOrder(OrderCertificateDetails orderCertificateDetails);

    List<Order> findOrdersByUserId(long userId);
}
