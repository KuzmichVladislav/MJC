package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService extends BaseService<UserDto> {

    List<OrderDto> findOrdersByUserId(String userId);
}
