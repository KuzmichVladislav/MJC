package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private User user;
    private UserDto userDto;
    private UserRegistrationDto userRegistrationDto;
    private UserService userService;
    private Pageable pageable;
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(modelMapper, passwordEncoder, userRepository);
        userDto = UserDto.builder().id(1L).username("Login").firstName("Name").lastName("Surname").build();
        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setId(1L);
        userRegistrationDto.setUsername("Login");
        userRegistrationDto.setPassword("Password");
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("Surname");
        user = User.builder().id(1L).username("Login").firstName("Name").lastName("Surname").build();
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
    void testFindById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
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

    @Test
    void testAdd_AllFieldsAreValid_CreatesUser() {
        // Given
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        // When
        UserDto result = userService.add(userRegistrationDto);
        // Then
        Assertions.assertEquals(userDto, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "a", "Name with space", "NameMoreThen16Char"})
    void testAdd_InvalidName_ExceptionThrown(String username) {
        // Given
        userDto.setUsername(username);
        // When
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        // Then
        Assertions.assertTrue(constraintViolations.size() > 0);
    }

    @Test
    void testLoadUserByUsername_UserExists_FindsUser() {
        // Given
        when(userRepository.findByUsername("Login")).thenReturn(java.util.Optional.ofNullable(user));
        // When
        UserRegistrationDto result = userService.loadUserByUsername("Login");
        result.setPassword("Password");
        // Then
        Assertions.assertEquals(userRegistrationDto, result);
    }
}
