package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificates;

public interface OrderDao extends BaseDao<Order> {

    void addGiftCertificateToOrder(OrderCertificates orderCertificates);
}
