package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.LinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * The Class OrderController is a Rest Controller class which will have
 * all end points for order which is includes POST, GET, DELETE.
 */
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final LinkCreator linkCreator;

    /**
     * Add order order based on POST request.
     *
     * @param orderDto the order DTO
     * @return the order DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto resultOrder = orderService.add(orderDto);
        linkCreator.addOrderLinks(resultOrder);
        return resultOrder;
    }

    /**
     * Gets all orders based on GET request.
     *
     * @param page             the number of page
     * @param size             the size of display items
     * @param sortingDirection the sorting direction
     * @return the all orders
     */
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
        orderPage.getItemsPerPage().forEach(linkCreator::addOrderLinks);
        return orderPage;
    }

    /**
     * Gets order by id based on GET request.
     *
     * @param id the identifier
     * @return the order identifier
     */
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") @Min(1) long id) {
        OrderDto resultOrder = orderService.findById(id);
        linkCreator.addOrderLinks(resultOrder);
        return resultOrder;
    }

    /**
     * Delete order based on DELETE request.
     *
     * @param id the identifier
     * @return the http entity
     */
    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteOrder(@PathVariable("id") @Min(1) long id) {
        orderService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Gets orders by user identifier based on GET request.
     *
     * @param userId the user identifier
     * @return the orders
     */
    @GetMapping("/users/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable("userId") long userId) {
        return orderService.findOrdersByUserId(userId);
    }
}
