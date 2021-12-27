package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.UserRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final OrderService orderService;
    private final UserRequestValidator userRequestValidator;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           OrderService orderService, UserRequestValidator userRequestValidator,
                           ModelMapper modelMapper, ListConverter listConverter) {
        this.userDao = userDao;
        this.orderService = orderService;
        this.userRequestValidator = userRequestValidator;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
    }

    @Override
    public UserDto add(UserDto tagDto) {
        // FIXME: 12/24/2021
        return null;
    }

    @Override
    public UserDto findById(String id) {
        // TODO: 12/26/2021
        long longId;
        try {
            longId = Long.parseLong(id);
            userRequestValidator.checkId(longId);
        } catch (NumberFormatException e) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
        return convertToUserDto(userDao.findById(longId).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(), id)));
    }

    @Override
    public List<UserDto> findAll() {
        return listConverter.convertList(userDao.findAll(), this::convertToUserDto);
    }

    @Override
    public boolean removeById(String id) {
        // FIXME: 12/24/2021
        return false;
    }

    @Override
    public List<OrderDto> findOrdersByUserId(String userId) {
        // FIXME: 12/26/2021
        long longId;
        try {
            longId = Long.parseLong(userId);
            userRequestValidator.checkId(longId);
        } catch (NumberFormatException e) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(userId));
        }
        return orderService.findOrdersByUserId(longId);
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
