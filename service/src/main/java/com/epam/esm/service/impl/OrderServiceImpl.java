package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.UserRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserRequestValidator userRequestValidator;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            UserRequestValidator userRequestValidator,
                            ModelMapper modelMapper, ListConverter listConverter) {
        this.orderDao = orderDao;
        this.userRequestValidator = userRequestValidator;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
    }

    @Override
    public OrderDto add(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto findById(String id) {
        long longId;
        try {
            longId = Long.parseLong(id);
            userRequestValidator.checkId(longId);
        } catch (NumberFormatException e) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));// TODO: 12/24/2021
        }
        return convertToOrderDto(orderDao.findById(longId).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(), id)));
    }

    @Override
    public List<OrderDto> findAll() {
        return listConverter.convertList(orderDao.findAll(),
                this::convertToOrderDto);
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    private OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
