package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.JwtAuthorizationException;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(long id) {
        return convertToUserDto(userDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public PagedModel<UserDto> findAll(QueryParameterDto queryParameterDto) {
        long totalNumberOfItems = userDao.getTotalNumberOfItems();
        if (totalNumberOfItems == 0) {
            return PagedModel.of(new ArrayList<>(), new PagedModel.PageMetadata(0, 1, 0, 1));
        }
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<UserDto> users = listConverter.convertList(userDao.findAll(modelMapper.map(queryParameterDto,
                QueryParameter.class)), this::convertToUserDto);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(queryParameterDto.getSize(),
                queryParameterDto.getPage(), totalNumberOfItems, totalPage);
        return PagedModel.of(users, pageMetadata);
    }

    @Override
    @Transactional
    public UserDto add(UserRegistrationDto registrationDto) {
        String username = registrationDto.getUsername();
        if (userDao.findByUsername(username).isEmpty()) {
            registrationDto.setRoles(Collections.singleton(RoleDto.USER));
            registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            User user = modelMapper.map(registrationDto, User.class);
            return findById(userDao.add(user).getId());
        } else {
            throw new RequestValidationException(ExceptionKey.USER_EXISTS, username);
        }
    }

    @Override
    @Transactional
    public UserRegistrationDto loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username).orElseThrow(() ->
                new JwtAuthorizationException(ExceptionKey.USERNAME_OR_PASSWORD_INCORRECT));
        return modelMapper.map(user, UserRegistrationDto.class);
    }

    @Override
    @Transactional
    public UserRegistrationDto findByUsernameAndPassword(String username, String password) {
        UserRegistrationDto userRegistrationDto = loadUserByUsername(username);
        if (passwordEncoder.matches(password, userRegistrationDto.getPassword())) {
            return userRegistrationDto;
        } else {
            throw new JwtAuthorizationException(ExceptionKey.USERNAME_OR_PASSWORD_INCORRECT);
        }
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
