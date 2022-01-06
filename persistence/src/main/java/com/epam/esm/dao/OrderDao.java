package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.entity.QueryParameter;

import java.util.List;

public interface OrderDao extends BaseDao<Order, QueryParameter> {

    void addGiftCertificateToOrder(OrderCertificateDetails orderCertificateDetails);

    List<Order> findOrdersByUserId(long userId);
}
