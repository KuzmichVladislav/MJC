package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificates;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {

    void addGiftCertificateToOrder(OrderCertificates orderCertificates);

    List<Order> findOrdersByUserId(long userId);
}
