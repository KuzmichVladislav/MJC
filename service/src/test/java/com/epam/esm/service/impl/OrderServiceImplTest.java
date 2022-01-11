package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.TotalPageCountCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
    @Mock
    OrderDao orderDao;
    @Mock
    GiftCertificateService giftCertificateService;
    @Mock
    TotalPageCountCalculator totalPageCountCalculator;
    ModelMapper modelMapper;
    OrderService orderService;
    OrderCertificateDetails orderCertificateDetails;
    GiftCertificate giftCertificate;
    GiftCertificateDto giftCertificateDto;
    Order order;
    OrderDto orderDto;
    OrderCertificateDetailsDto orderCertificateDetailsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        orderService = new OrderServiceImpl(orderDao,
                modelMapper,
                giftCertificateService,
                totalPageCountCalculator);
        giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .lastUpdateDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .tags(Collections.singletonList(new Tag(1L, "name")))
                .build();
        giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .lastUpdateDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .tags(Collections.singletonList(new TagDto(1L, "name")))
                .build();
        orderCertificateDetails = OrderCertificateDetails.builder()
                .giftCertificate(giftCertificate)
                .order(order)
                .numberOfCertificates(1)
                .giftCertificateCost(new BigDecimal(1))
                .build();
        orderCertificateDetailsDto = OrderCertificateDetailsDto.builder()
                .giftCertificate(giftCertificateDto)
                .price(new BigDecimal(1))
                .numberOfCertificates(1)
                .build();
        order = Order.builder()
                .id(1L)
                .userId(1L)
                .purchaseTime(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .orderCertificateDetails(new HashSet<>(Collections.singletonList(orderCertificateDetails)))
                .build();
        orderDto = OrderDto.builder()
                .id(1L)
                .totalCost(new BigDecimal(1))
                .userId(1L)
                .purchaseTime(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .orderCertificateDetailsDtos(Collections.singletonList(orderCertificateDetailsDto))
                .build();
    }


    @Test
    void testAdd_AllFieldsAreValid_CreatesOrder() {
        // Given
        when(orderDao.add(any())).thenReturn(order);
        when(giftCertificateService.findById(anyLong())).thenReturn(giftCertificateDto);
        // When
        OrderDto result = orderService.add(orderDto);
        orderDto.setPurchaseTime(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43));
        // Then
        Assertions.assertEquals(orderDto, result);
    }

    @Test
    void testFindById_ValidId_findsOrder() {
        // Given
        when(orderDao.findById(1L)).thenReturn(Optional.ofNullable(order));
        // When
        OrderDto result = orderService.findById(1L);
        // Then
        Assertions.assertEquals(orderDto, result);
    }

    @Test
    void testFindAll_OrdersExist_findsOrders() {
        // Given
        when(orderDao.findAll(any())).thenReturn(Collections.singletonList(order));
        when(orderDao.getTotalNumberOfItems()).thenReturn(1L);
        when(totalPageCountCalculator.getTotalPage(any(), anyLong())).thenReturn(1);
        // When
        PageWrapper<OrderDto> result = orderService.findAll(new QueryParameterDto());
        // Then
        Assertions.assertEquals(new PageWrapper<>(Collections.singletonList(orderDto), 1), result);
    }

    @Test
    void testFindOrdersByUserId_ValidUserId_findsOrder() {
        // Given
        when(orderDao.findOrdersByUserId(1L)).thenReturn(Collections.singletonList(order));
        // When
        List<OrderDto> result = orderService.findOrdersByUserId(1L);
        // Then
        Assertions.assertEquals(Collections.singletonList(orderDto), result);
    }
}
