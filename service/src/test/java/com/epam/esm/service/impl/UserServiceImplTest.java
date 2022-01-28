package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private User user;
    private UserDto userDto;
    private UserService userService;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(modelMapper,
                passwordEncoder,
                userRepository);
        userDto = UserDto.builder()
                .id(1L)
                .username("Login")
                .firstName("Name")
                .lastName("Surname")
                .build();
        user = User.builder()
                .id(1L)
                .username("Login")
                .firstName("Name")
                .lastName("Surname")
                .build();
        pageable = PageRequest.of(1, 20, Sort.Direction.ASC, "id");
    }

    @Test
    void testFindById_ValidId_findsUser() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        // When
        UserDto result = userService.findById(1L);
        // Then
        Assertions.assertEquals(userDto, result);
    }

    @Test
    void testFindAll_UsersExist_findsUsers() {
        // Given
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(user)));
        // When
        Page<UserDto> result = userService.findAll(pageable);
        // Then
        Assertions.assertEquals(1, result.getContent().size());
    }
}
