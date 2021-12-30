package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.LinkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final LinkCreator linkCreator;

    @Autowired
    public UserController(UserService userService,
                          LinkCreator linkCreator) {
        this.userService = userService;
        this.linkCreator = linkCreator;
    }

//    @GetMapping(params = {"page", "size"})
//    public List<UserDto> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        List<UserDto> users = userService.findAll(page, size);
//        users.forEach(linkCreator::addUserLinks);
//        return users;
//    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") String id) {
        UserDto user = userService.findById(id);
        linkCreator.addUserLinks(user);
        return user;
    }
}
