package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService extends BaseService<OrderDto> {

    List<OrderDto> findOrdersByUserId(long userId);
}
