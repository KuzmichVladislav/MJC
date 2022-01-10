package com.epam.esm.controller;

import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.LinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * The Class UserController is a Rest Controller class which will have
 * all end points for user which is include GET request.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LinkCreator linkCreator;

    /**
     * Gets all users based on GET request.
     *
     * @param page             the page
     * @param size             the size
     * @param sortingDirection the sorting direction
     * @return the all users
     */
    @GetMapping
    public PageWrapper<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            @RequestParam(value = "order-by", required = false, defaultValue = "ASC")
                                                    QueryParameterDto.SortingDirection sortingDirection) {
        QueryParameterDto queryParameterDto = QueryParameterDto.builder()
                .page(page)
                .size(size)
                .sortingDirection(sortingDirection)
                .build();
        PageWrapper<UserDto> userPage = userService.findAll(queryParameterDto);
        userPage.getItemsPerPage().forEach(linkCreator::addUserLinks);
        linkCreator.addUserPaginationLinks(queryParameterDto, userPage);
        return userPage;
    }

    /**
     * Gets user by id based on GET request.
     *
     * @param id the identifier
     * @return the user by identifier
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @Min(1) long id) {
        UserDto user = userService.findById(id);
        linkCreator.addUserLinks(user);
        return user;
    }
}
