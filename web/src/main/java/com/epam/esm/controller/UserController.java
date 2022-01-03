package com.epam.esm.controller;

import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.LinkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

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

    @GetMapping
    public PageWrapper<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            @RequestParam(value = "order-by", required = false, defaultValue = "ASC") QueryParameterDto.SortingDirection sortingDirection) {

        QueryParameterDto queryParameterDto = QueryParameterDto.builder()
                .page(page)
                .size(size)
                .sortingDirection(sortingDirection)
                .build();
        PageWrapper<UserDto> userPage = userService.findAll(queryParameterDto);
        userPage.getPageValues().forEach(linkCreator::addUserLinks);
        return userPage;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @Min(1) long id) {
        UserDto user = userService.findById(id);
        linkCreator.addUserLinks(user);
        return user;
    }
}
