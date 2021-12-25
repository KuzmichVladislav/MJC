package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
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
import java.util.List;
import java.util.Map;
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
        Order order = modelMapper.map(orderDto, Order.class);
        order.setPurchaseTime(LocalDateTime.now());
        OrderDto orderDto1 = modelMapper.map(orderDao.add(order), OrderDto.class);
        List<GiftCertificateDto> giftCertificates = orderDto.getOrderCertificateDetails().stream()
                .map(t -> giftCertificateService.findById(String.valueOf(t.getGiftCertificateId())))
                .collect(Collectors.toList());
        Map<GiftCertificateDto, Long> collect = giftCertificates.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()));
        collect.forEach((g, c) -> {
            OrderCertificates orderCertificates = new OrderCertificates();
            orderCertificates.setOrder(modelMapper.map(orderDto1, Order.class));
            orderCertificates.setGiftCertificate(modelMapper.map(g, GiftCertificate.class));
            orderCertificates.setGiftCertificateCost(g.getPrice());
            orderCertificates.setNumberOfCertificates(Math.toIntExact(c));
            orderDao.addGiftCertificateToOrder(orderCertificates);
        });
        return orderDto1;
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
/*
 FIXME: 12/25/2021
        Order order = orderDao.findById(longId).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(), id));
        Map<Map<GiftCertificateDto, BigDecimal>, Integer> giftCertificateDetails = new HashMap<>();
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        order.getOrderCertificates().stream()
                .forEach(oc -> {
                    giftCertificateDetails.put(new HashMap<>(){{put(modelMapper.map(oc.getGiftCertificate(),
                                    GiftCertificateDto.class), oc.getGiftCertificateCost());}}, oc.getNumberOfCertificates());
                    totalCost[0] = totalCost[0].add((oc.getGiftCertificateCost().multiply(BigDecimal.valueOf(oc.getNumberOfCertificates()))));
                });
        orderDto.setGiftCertificateDetails(giftCertificateDetails);
        orderDto.setTotalCost(totalCost[0]);
        return orderDto;
*/
        Order order = orderDao.findById(longId).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(), id));
        return getOrderDto(order);
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> orders = orderDao.findAll();
        return orders.stream()
                .map(this::getOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    private OrderDto getOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        final BigDecimal[] totalCost = {new BigDecimal(0)};
        List<OrderDto.OrderCertificateDetails> collect = order.getOrderCertificates().stream()
                .map(t -> OrderDto.OrderCertificateDetails.builder()
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
