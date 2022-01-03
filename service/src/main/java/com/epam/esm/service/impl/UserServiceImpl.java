package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;
    private final TotalPageCountCalculator totalPageCountCalculator;


    @Autowired
    public UserServiceImpl(UserDao userDao,
                           ModelMapper modelMapper,
                           ListConverter listConverter,
                           TotalPageCountCalculator totalPageCountCalculator) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
        this.totalPageCountCalculator = totalPageCountCalculator;
    }

    @Override
    public UserDto add(UserDto userDto) {
        // FIXME: 12/24/2021
        return null;
    }

    @Override
    public UserDto findById(long id) {
        return convertToUserDto(userDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, String.valueOf(id))));
    }

//    @Override
//    public UserDto findById(String id) {
//        // TODO: 12/26/2021
//        long longId;
//        try {
//            longId = Long.parseLong(id);
//            userRequestValidator.checkId(longId);
//        } catch (NumberFormatException e) {
//            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID, String.valueOf(id));
//        }
//        return convertToUserDto(userDao.findById(longId).orElseThrow(() ->
//                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, id)));
//    }

    @Override
    public PageWrapper<UserDto> findAll(QueryParameterDto queryParameterDto) {
        long totalNumberOfItems = userDao.getTotalNumberOfItems();
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<UserDto> users = listConverter.convertList(userDao.findAll(modelMapper.map(queryParameterDto, QueryParameter.class)), this::convertToUserDto);
        return new PageWrapper<>(users, totalPage);
    }


    @Override
    public boolean removeById(long id) {
        // FIXME: 12/24/2021
        return false;
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
