package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.QueryParameterDto;

import java.util.List;

public interface OrderService extends BaseService<OrderDto, QueryParameterDto> {

    List<OrderDto> findOrdersByUserId(long userId);
}
