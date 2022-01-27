package com.epam.esm.service.impl;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.JwtAuthorizationException;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * The Class UserServiceImpl is the implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDto findById(long id) {
        return convertToUserDto(userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToUserDto);
    }

    @Override
    @Transactional
    public UserDto add(UserRegistrationDto registrationDto) {
        String username = registrationDto.getUsername();
        if (userRepository.findByUsername(username).isEmpty()) {
            registrationDto.setRoles(Collections.singleton(RoleDto.USER));
            registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            User user = modelMapper.map(registrationDto, User.class);
            return findById(userRepository.save(user).getId());
        } else {
            throw new RequestValidationException(ExceptionKey.USER_EXISTS, username);
        }
    }

    @Override
    @Transactional
    public UserRegistrationDto loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
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
