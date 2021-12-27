package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        // TODO: 12/27/2021
//        List<UserDto> users = userService.findAll();
//        users.forEach(u -> addLinks(String.valueOf(u.getId()), u));
//        return users;
        return null;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") String id) {
        UserDto user = userService.findById(id);
        addLinks(id, user);
        return user;
    }

    @GetMapping("/{id}/orders")
    // FIXME: 12/27/2021
    public List<OrderDto> getOrdersByUserId(@PathVariable("id") String userId) {
        return userService.findOrdersByUserId(userId);
    }

    private void addLinks(String id, UserDto user) {
        user.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
    }
}
