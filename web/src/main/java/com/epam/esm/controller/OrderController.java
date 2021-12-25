package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        return orderService.add(orderDto);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") String id) {
        return orderService.findById(id);
    }
}
