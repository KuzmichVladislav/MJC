package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        OrderDto resultOrder = orderService.add(orderDto);
        addLinks(String.valueOf(resultOrder.getId()), resultOrder);
        return resultOrder;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        // TODO: 12/27/2021
//        List<OrderDto> orders = orderService.findAll();
//        orders.forEach(o -> addLinks(String.valueOf(o.getId()), o));
//        return orders;
        return null;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") String id) {
        OrderDto resultOrder = orderService.findById(id);
        addLinks(id, resultOrder);
        return resultOrder;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteOrder(@PathVariable("id") String id) {
        orderService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addLinks(String id, OrderDto resultOrder) {
        resultOrder.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        resultOrder.add(linkTo(methodOn(OrderController.class).deleteOrder(id)).withRel("delete"));
    }
}
