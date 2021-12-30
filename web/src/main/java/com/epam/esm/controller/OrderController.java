package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.LinkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        OrderDto resultOrder = orderService.add(orderDto);
        linkCreator.addOrderLinks(resultOrder);
        return resultOrder;
    }
//
//    @GetMapping(params = {"page", "size"})
//    public List<OrderDto> getAllOrders(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        List<OrderDto> orders = orderService.findAll(page, size);
//        orders.forEach(linkCreator::addOrderLinks);
//        return orders;
//    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") String id) {
        OrderDto resultOrder = orderService.findById(id);
        linkCreator.addOrderLinks(resultOrder);
        return resultOrder;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteOrder(@PathVariable("id") String id) {
        orderService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable("userId") long userId) {
        return orderService.findOrdersByUserId(userId);
    }
}
