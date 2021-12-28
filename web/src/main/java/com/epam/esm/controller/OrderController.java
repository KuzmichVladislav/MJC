package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
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

    @GetMapping(params = {"page", "size"})
    public List<OrderDto> getAllOrders(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<OrderDto> orders = orderService.findAll(page, size);
        orders.forEach(o -> addLinks(String.valueOf(o.getId()), o));
        return orders;
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
        resultOrder.getOrderCertificateDetails().forEach(od -> {
            GiftCertificateDto giftCertificate = od.getGiftCertificate();
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .getGiftCertificateById(String.valueOf(od.getGiftCertificate().getId()))).withSelfRel());
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .updateGiftCertificate(String.valueOf(giftCertificate.getId()), giftCertificate)).withRel("update"));
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .deleteGiftCertificate(String.valueOf(giftCertificate.getId()))).withRel("delete"));
            List<TagDto> tags = giftCertificate.getTags();
            if (tags != null) {
                tags.forEach(t -> {
                    t.add(linkTo(methodOn(TagController.class).getTagById(String.valueOf(t.getId()))).withSelfRel());
                    t.add(linkTo(methodOn(TagController.class).deleteTag(String.valueOf(t.getId()))).withRel("delete"));
                });
            }
        });
    }
}
