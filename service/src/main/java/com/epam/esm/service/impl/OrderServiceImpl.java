package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.TotalPageCountCalculator;
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
    private final ModelMapper modelMapper;
    private final GiftCertificateService giftCertificateService;
    private final TotalPageCountCalculator totalPageCountCalculator;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            ModelMapper modelMapper,
                            GiftCertificateService giftCertificateService,
                            TotalPageCountCalculator totalPageCountCalculator) {
        this.orderDao = orderDao;
        this.totalPageCountCalculator = totalPageCountCalculator;
        this.modelMapper = modelMapper;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    @Transactional
    public OrderDto add(OrderDto orderDto) {
        orderDto.setPurchaseTime(LocalDateTime.now());
        Order order = orderDao.add(modelMapper.map(orderDto, Order.class));
        List<GiftCertificateDto> giftCertificates = orderDto.getOrderCertificateDetails().stream()
                .map(t -> giftCertificateService.findById(t.getGiftCertificate().getId()))
                .collect(Collectors.toList());
        Map<GiftCertificateDto, Long> collect = giftCertificates.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()));
        Set<OrderCertificateDetails> orderCertificateDetailsSet = new HashSet<>();
        collect.forEach((g, c) -> {
            OrderCertificateDetails orderCertificateDetails = new OrderCertificateDetails();
            orderCertificateDetails.setOrder(order);
            orderCertificateDetails.setGiftCertificate(modelMapper.map(g, GiftCertificate.class));
            orderCertificateDetails.setGiftCertificateCost(g.getPrice());
            orderCertificateDetails.setNumberOfCertificates(Math.toIntExact(c));
            orderDao.addGiftCertificateToOrder(orderCertificateDetails);
            orderCertificateDetailsSet.add(orderCertificateDetails);
        });
        order.setOrderCertificateDetails(orderCertificateDetailsSet);
        return getOrderDto(order);
    }

    @Override
    public OrderDto findById(long id) {
        Order order = orderDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, String.valueOf(id)));
        return getOrderDto(order);
    }

    @Override
    public PageWrapper<OrderDto> findAll(QueryParameterDto queryParameterDto) {
        long totalNumberOfItems = orderDao.getTotalNumberOfItems();
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<Order> orders = orderDao.findAll(modelMapper.map(queryParameterDto, QueryParameter.class));
        List<OrderDto> orderDtos = orders.stream().map(this::getOrderDto).collect(Collectors.toList());
        return new PageWrapper<>(orderDtos, totalPage);
    }

    @Override
    public boolean removeById(long id) {
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
        List<OrderCertificateDetailsDto> orderCertificateDetails = getOrderCertificateDetails(order);
        orderDto.setOrderCertificateDetails(orderCertificateDetails);
        orderCertificateDetails.forEach(t -> totalCost[0] = totalCost[0].add(t.getPrice().multiply(BigDecimal.valueOf(t.getNumberOfCertificates()))));
        orderDto.setTotalCost(totalCost[0]);
        return orderDto;
    }

    private List<OrderCertificateDetailsDto> getOrderCertificateDetails(Order order) {
        return order.getOrderCertificateDetails().stream()
                .map(t -> {
                    GiftCertificateDto giftCertificate = modelMapper.map(t.getGiftCertificate(), GiftCertificateDto.class);
                    giftCertificate.setPrice(t.getGiftCertificateCost());
                    return OrderCertificateDetailsDto.builder()
                            .giftCertificate(giftCertificate)
                            .price(t.getGiftCertificateCost())
                            .numberOfCertificates(t.getNumberOfCertificates())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
