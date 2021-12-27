package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificates;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.UserRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserRequestValidator userRequestValidator;
    private final ModelMapper modelMapper;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            UserRequestValidator userRequestValidator,
                            ModelMapper modelMapper,
                            GiftCertificateService giftCertificateService) {
        this.orderDao = orderDao;
        this.userRequestValidator = userRequestValidator;
        this.modelMapper = modelMapper;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    @Transactional
    public OrderDto add(OrderDto orderDto) {
        orderDto.setPurchaseTime(LocalDateTime.now());
        Order order = orderDao.add(modelMapper.map(orderDto, Order.class));
        List<GiftCertificateDto> giftCertificates = orderDto.getOrderCertificateDetails().stream()
                .map(t -> giftCertificateService.findById(String.valueOf(t.getGiftCertificateId())))
                .collect(Collectors.toList());
        Map<GiftCertificateDto, Long> collect = giftCertificates.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()));
        Set<OrderCertificates> orderCertificatesSet = new HashSet<>();
        collect.forEach((g, c) -> {
            OrderCertificates orderCertificates = new OrderCertificates();
            orderCertificates.setOrder(order);
            orderCertificates.setGiftCertificate(modelMapper.map(g, GiftCertificate.class));
            orderCertificates.setGiftCertificateCost(g.getPrice());
            orderCertificates.setNumberOfCertificates(Math.toIntExact(c));
            orderDao.addGiftCertificateToOrder(orderCertificates);
            orderCertificatesSet.add(orderCertificates);
        });
        order.setOrderCertificates(orderCertificatesSet);
        return getOrderDto(order);
    }

    @Override
    public OrderDto findById(String id) {
        long longId;
        try {
            longId = Long.parseLong(id);
            userRequestValidator.checkId(longId);
        } catch (NumberFormatException e) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));// TODO: 12/24/2021
        }
        Order order = orderDao.findById(longId).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(), id));
        return getOrderDto(order);
    }


    @Override
    public List<OrderDto> findAll(int page, int size) {
        // TODO: 12/27/2021
//        List<Order> orders = orderDao.findAll();
//        return orders.stream()
//                .map(this::getOrderDto)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public boolean removeById(String id) {
        OrderDto orderDto = findById(id);
        return orderDao.remove(modelMapper.map(orderDto, Order.class));
    }

    @Override
    public List<OrderDto> findOrdersByUserId(long userId) {
        List<Order> orders = orderDao.findOrdersByUserId(userId);
        return orders.stream()
                .map(this::getOrderDto)
                .collect(Collectors.toList());
    }

    private OrderDto getOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        final BigDecimal[] totalCost = {new BigDecimal(0)};
        List<OrderCertificateDetailsDto> collect = order.getOrderCertificates().stream()
                .map(t -> OrderCertificateDetailsDto.builder()
                        .giftCertificateId(t.getGiftCertificate().getId())
                        .price(t.getGiftCertificateCost())
                        .numberOfCertificates(t.getNumberOfCertificates())
                        .build())
                .collect(Collectors.toList());
        orderDto.setOrderCertificateDetails(collect);
        collect.forEach(t -> totalCost[0] = totalCost[0].add(t.getPrice().multiply(BigDecimal.valueOf(t.getNumberOfCertificates()))));
        orderDto.setTotalCost(totalCost[0]);
        return orderDto;
    }
}
