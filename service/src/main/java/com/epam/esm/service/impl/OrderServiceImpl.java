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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

/**
 * The Class OrderServiceImpl is the implementation of the {@link OrderService} interface.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final GiftCertificateService giftCertificateService;
    private final TotalPageCountCalculator totalPageCountCalculator;

    @Override
    @Transactional
    public OrderDto add(OrderDto orderDto) {
        Order order = orderDao.add(modelMapper.map(orderDto, Order.class));
        Set<OrderCertificateDetails> orderCertificateDetailsSet = new HashSet<>();
        orderDto.getOrderCertificateDetailsDtos().stream()
                .map(t -> giftCertificateService.findById(t.getGiftCertificate().getId()))
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .forEach((g, c) -> {
                    OrderCertificateDetails orderCertificateDetails = getOrderCertificateDetails(order, g, c);
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
    @Transactional
    public PageWrapper<OrderDto> findAll(QueryParameterDto queryParameterDto) {
        long totalNumberOfItems = orderDao.getTotalNumberOfItems();
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<Order> orders = orderDao.findAll(modelMapper.map(queryParameterDto, QueryParameter.class));
        List<OrderDto> orderDtos = orders.stream().map(this::getOrderDto).collect(Collectors.toList());
        return new PageWrapper<>(orderDtos, totalPage);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        orderDao.remove(orderDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.ORDER_NOT_FOUND, String.valueOf(id))));
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
        List<OrderCertificateDetailsDto> orderCertificateDetails = getOrderCertificateDetails(order);
        orderDto.setOrderCertificateDetailsDtos(orderCertificateDetails);
        BigDecimal totalCost = orderCertificateDetails.stream()
                .map(details -> details.getPrice().multiply(BigDecimal.valueOf(details.getNumberOfCertificates())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDto.setTotalCost(totalCost);
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

    private OrderCertificateDetails getOrderCertificateDetails(Order order, GiftCertificateDto giftCertificateDto, Long giftCertificateCount) {
        return OrderCertificateDetails.builder()
                .order(order)
                .giftCertificate(modelMapper.map(giftCertificateDto, GiftCertificate.class))
                .giftCertificateCost(giftCertificateDto.getPrice())
                .numberOfCertificates(Math.toIntExact(giftCertificateCount))
                .build();
    }
}
