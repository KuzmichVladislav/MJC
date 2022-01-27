package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderCertificateDetailsDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificateDetails;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.OrderCertificateDetailsRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
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

    private final ModelMapper modelMapper;
    private final GiftCertificateService giftCertificateService;
    private final OrderRepository orderRepository;
    private final OrderCertificateDetailsRepository orderCertificateDetailsRepository;

    @Override
    @Transactional
    public OrderDto add(OrderDto orderDto) {
        Order order = orderRepository.save(modelMapper.map(orderDto, Order.class));
        Set<OrderCertificateDetails> orderCertificateDetailsSet = new HashSet<>();
        orderDto.getOrderCertificateDetailsDtos().stream()
                .map(t -> giftCertificateService.findById(t.getGiftCertificate().getId()))
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .forEach((g, c) -> {
                    OrderCertificateDetails orderCertificateDetails = getOrderCertificateDetails(order, g, c);
                    orderCertificateDetailsRepository.save(orderCertificateDetails);
                    orderCertificateDetailsSet.add(orderCertificateDetails);
                });
        order.setOrderCertificateDetails(orderCertificateDetailsSet);
        return getOrderDto(order);
    }

    @Override
    public OrderDto findById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.ORDER_NOT_FOUND, String.valueOf(id)));
        return getOrderDto(order);
    }

    @Override
    public List<OrderDto> findOrdersByUserId(long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::getOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PostAuthorize("returnObject.userId == principal.userId or hasAuthority('ADMIN')")
    public OrderDto removeById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.ORDER_NOT_FOUND, String.valueOf(id)));
        orderRepository.delete(order);
        return convertToOrderDto(order);
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderDto> orderDtos = orders.stream().map(this::getOrderDto).collect(Collectors.toList());
        return new PageImpl<>(orderDtos, orders.getPageable(), orders.getTotalElements());
    }

    private OrderDto getOrderDto(Order order) {
        OrderDto orderDto = convertToOrderDto(order);
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

    private OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
