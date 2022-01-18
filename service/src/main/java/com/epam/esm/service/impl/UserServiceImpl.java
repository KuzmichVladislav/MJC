package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Class UserServiceImpl is the implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;
    private final TotalPageCountCalculator totalPageCountCalculator;

    @Override
    public UserDto findById(long id) {
        return convertToUserDto(userDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public PagedModel<UserDto> findAll(QueryParameterDto queryParameterDto) {
        long totalNumberOfItems = userDao.getTotalNumberOfItems();
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<UserDto> users = listConverter.convertList(userDao.findAll(modelMapper.map(queryParameterDto,
                QueryParameter.class)), this::convertToUserDto);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(queryParameterDto.getSize(),
                queryParameterDto.getPage(), totalNumberOfItems, totalPage);
        return PagedModel.of(users, pageMetadata);
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
