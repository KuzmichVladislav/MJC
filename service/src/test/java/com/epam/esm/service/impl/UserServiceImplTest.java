//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.UserDao;
//import com.epam.esm.dto.QueryParameterDto;
//import com.epam.esm.dto.UserDto;
//import com.epam.esm.entity.User;
//import com.epam.esm.service.UserService;
//import com.epam.esm.util.ListConverter;
//import com.epam.esm.util.TotalPageCountCalculator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.hateoas.PagedModel;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.anyLong;
//import static org.mockito.Mockito.when;
//
//class UserServiceImplTest {
//
//    @Mock
//    private UserDao userDao;
//    private ModelMapper modelMapper;
//    private ListConverter listConverter;
//    private TotalPageCountCalculator totalPageCountCalculator;
//    private User user;
//    private UserDto userDto;
//    private UserService userService;
//    private QueryParameterDto queryParameter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        modelMapper = new ModelMapper();
//        listConverter = new ListConverter();
//        totalPageCountCalculator = new TotalPageCountCalculator();
//        userService = new UserServiceImpl(userDao,
//                modelMapper,
//                listConverter,
//                totalPageCountCalculator);
//        userDto = UserDto.builder()
//                .id(1L)
//                .username("Login")
//                .firstName("Name")
//                .lastName("Surname")
//                .build();
//        user = User.builder()
//                .id(1L)
//                .login("Login")
//                .firstName("Name")
//                .lastName("Surname")
//                .build();
//        queryParameter = QueryParameterDto.builder()
//                .page(1)
//                .size(10)
//                .firstValue(1)
//                .sortingDirection(QueryParameterDto.SortingDirection.ASC)
//                .build();
//    }
//
//    @Test
//    void testFindById_ValidId_findsUser() {
//        // Given
//        when(userDao.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
//        // When
//        UserDto result = userService.findById(1L);
//        // Then
//        Assertions.assertEquals(userDto, result);
//    }
//
//    @Test
//    void testFindAll_UsersExist_findsUsers() {
//        // Given
//        when(userDao.findAll(any())).thenReturn(Collections.singletonList(user));
//        when(userDao.getTotalNumberOfItems()).thenReturn(20L);
//        // When
//        PagedModel<UserDto> result = userService.findAll(queryParameter);
//        // Then
//        Assertions.assertEquals(Collections.singletonList(userDto), new ArrayList<>(result.getContent()));
//    }
//}
