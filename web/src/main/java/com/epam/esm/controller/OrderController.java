package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.LinkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final LinkCreator linkCreator;

    @Autowired
    public OrderController(OrderService orderService,
                           LinkCreator linkCreator) {
        this.orderService = orderService;
        this.linkCreator = linkCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto resultOrder = orderService.add(orderDto);
        linkCreator.addOrderLinks(resultOrder);
        return resultOrder;
    }

    @GetMapping
    public PageWrapper<OrderDto> getAllOrders(@RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "10") int size,
                                              @RequestParam(value = "order-by", required = false, defaultValue = "ASC")
                                                      QueryParameterDto.SortingDirection sortingDirection) {
        QueryParameterDto queryParameterDto = QueryParameterDto.builder()
                .page(page)
                .size(size)
                .sortingDirection(sortingDirection)
                .build();
        PageWrapper<OrderDto> orderPage = orderService.findAll(queryParameterDto);
        orderPage.getPageValues().forEach(linkCreator::addOrderLinks);
        return orderPage;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") @Min(1) long id) {
        OrderDto resultOrder = orderService.findById(id);
        linkCreator.addOrderLinks(resultOrder);
        return resultOrder;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteOrder(@PathVariable("id") @Min(1) long id) {
        orderService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable("userId") long userId) {
        return orderService.findOrdersByUserId(userId);
    }
}
