package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderCertificateDetailsDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.ApplicationPage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ListConverter;
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
    private final ListConverter listConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            UserRequestValidator userRequestValidator,
                            ModelMapper modelMapper,
                            GiftCertificateService giftCertificateService, ListConverter listConverter) {
        this.orderDao = orderDao;
        this.userRequestValidator = userRequestValidator;
        this.modelMapper = modelMapper;
        this.giftCertificateService = giftCertificateService;
        this.listConverter = listConverter;
    }

    @Override
    @Transactional
    public OrderDto add(OrderDto orderDto) {
        orderDto.setPurchaseTime(LocalDateTime.now());
        Order order = orderDao.add(modelMapper.map(orderDto, Order.class));
        List<GiftCertificateDto> giftCertificates = orderDto.getOrderCertificateDetails().stream()
                .map(t -> giftCertificateService.findById(String.valueOf(t.getGiftCertificate())))
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
        order.setOrderCertificates(orderCertificateDetailsSet);
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
        int totalPage = (int) Math.ceil(orderDao.getTotalNumberOfItems() / (double) size);
        if (page > totalPage) {
            // TODO: 12/27/2021 throw new exception
        }
        ApplicationPage tagPage = ApplicationPage.builder()
                .size(size)
                .firstValue(page * size - size)
                .totalPage(totalPage)
                .build();
        return orderDao.findAll(tagPage).stream()
                .map(this::getOrderDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
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
        List<OrderCertificateDetailsDto> orderCertificateDetails = getOrderCertificateDetails(order);
        orderDto.setOrderCertificateDetails(orderCertificateDetails);
        orderCertificateDetails.forEach(t -> totalCost[0] = totalCost[0].add(t.getPrice().multiply(BigDecimal.valueOf(t.getNumberOfCertificates()))));
        orderDto.setTotalCost(totalCost[0]);
        return orderDto;
    }

    private List<OrderCertificateDetailsDto> getOrderCertificateDetails(Order order) {
        return order.getOrderCertificates().stream()
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
