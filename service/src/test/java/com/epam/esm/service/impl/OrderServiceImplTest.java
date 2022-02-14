package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderCertificateDetailsDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.OrderCertificateDetailsRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

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
    private OrderRepository orderRepository;
    @Mock
    private GiftCertificateService giftCertificateService;
    @Mock
    private OrderCertificateDetailsRepository orderCertificateDetailsRepository;
    private OrderService orderService;
    private GiftCertificateDto giftCertificateDto;
    private Order order;
    private OrderDto orderDto;
    private Pageable pageable;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        orderService = new OrderServiceImpl(modelMapper,
                giftCertificateService,
                orderRepository,
                orderCertificateDetailsRepository);
        GiftCertificate giftCertificate = GiftCertificate.builder()
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
        OrderCertificateDetails orderCertificateDetails = OrderCertificateDetails.builder()
                .giftCertificate(giftCertificate)
                .order(order)
                .numberOfCertificates(1)
                .giftCertificateCost(new BigDecimal(1))
                .build();
        OrderCertificateDetailsDto orderCertificateDetailsDto = OrderCertificateDetailsDto.builder()
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
        pageable = PageRequest.of(1, 20, Sort.Direction.ASC, "id");
    }


    @Test
    void testAdd_AllFieldsAreValid_CreatesOrder() {
        // Given
        when(orderRepository.save(any())).thenReturn(order);
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
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));
        // When
        OrderDto result = orderService.findById(1L);
        // Then
        Assertions.assertEquals(orderDto, result);
    }

    @Test
    void testFindAll_OrdersExist_findsOrders() {
        // Given
        when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(order)));
        // When
        Page<OrderDto> result = orderService.findAll(pageable);
        // Then
        Assertions.assertEquals(1, result.getContent().size());
    }

    @Test
    void testFindOrdersByUserId_ValidUserId_findsOrder() {
        // Given
        when(orderRepository.findByUserId(1L)).thenReturn(Collections.singletonList(order));
        // When
        List<OrderDto> result = orderService.findOrdersByUserId(1L);
        // Then
        Assertions.assertEquals(Collections.singletonList(orderDto), result);
    }
}
