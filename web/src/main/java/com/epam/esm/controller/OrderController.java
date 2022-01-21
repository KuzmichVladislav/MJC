package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.LinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.epam.esm.exception.ExceptionKey.ID_MIGHT_NOT_BE_NEGATIVE;
import static com.epam.esm.exception.ExceptionKey.PAGE_MIGHT_NOT_BE_NEGATIVE;
import static com.epam.esm.exception.ExceptionKey.SIZE_MIGHT_NOT_BE_NEGATIVE;

/**
 * The Class OrderController is a Rest Controller class which will have
 * all end points for order which is includes POST, GET, DELETE.
 */
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Validated
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
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<OrderDto> getAllOrders(@Min(value = 1, message = PAGE_MIGHT_NOT_BE_NEGATIVE)
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @Min(value = 1, message = SIZE_MIGHT_NOT_BE_NEGATIVE)
                                             @RequestParam(required = false, defaultValue = "10") int size,
                                             @RequestParam(value = "order-by", required = false, defaultValue = "ASC")
                                                     QueryParameterDto.SortingDirection sortingDirection) {
        QueryParameterDto queryParameterDto = QueryParameterDto.builder()
                .page(page)
                .size(size)
                .sortingDirection(sortingDirection)
                .build();
        PagedModel<OrderDto> orderPage = orderService.findAll(queryParameterDto);
        orderPage.getContent().forEach(linkCreator::addOrderLinks);
        linkCreator.addOrderPaginationLinks(queryParameterDto, orderPage);
        return orderPage;
    }

    /**
     * Gets order by id based on GET request.
     *
     * @param id the identifier
     * @return the order identifier
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDto getOrderById(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                                 @PathVariable("id") long id) {
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Void> deleteOrder(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                                        @PathVariable("id") long id) {
        orderService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Gets orders by user identifier based on GET request.
     *
     * @param userId the user identifier
     * @return the orders
     */
    @GetMapping("/users/{userId}")// TODO: 1/20/2022 get id from jwt?
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderDto> getOrdersByUserId(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                                            @PathVariable("userId") long userId) {
        return orderService.findOrdersByUserId(userId);
    }
}
