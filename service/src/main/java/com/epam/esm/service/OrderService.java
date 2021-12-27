package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService extends BaseService<OrderDto> {

   List<OrderDto> findOrdersByUserId(long userId);
}
