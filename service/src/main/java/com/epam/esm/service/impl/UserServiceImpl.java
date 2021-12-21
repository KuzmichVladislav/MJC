package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
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
    private final UserRequestValidator userRequestValidator;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           UserRequestValidator userRequestValidator,
                           ModelMapper modelMapper, ListConverter listConverter) {
        this.userDao = userDao;
        this.userRequestValidator = userRequestValidator;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
    }

    @Override
    public UserDto findById(String id) {
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

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
