package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.LinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import static com.epam.esm.exception.ExceptionKey.*;

/**
 * The Class UserController is a Rest Controller class which will have
 * all end points for user which is included GET request.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final LinkCreator linkCreator;
    private final PagedResourcesAssembler<UserDto> pagedResourcesAssembler;

    /**
     * Gets all users based on GET request.
     *
     * @param page             the page
     * @param size             the size
     * @param sortingDirection the sorting direction
     * @return the all users
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<UserDto>> getAllUsers(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = PAGE_MIGHT_NOT_BE_NEGATIVE) int page,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = SIZE_MIGHT_NOT_BE_NEGATIVE) int size,
            @RequestParam(value = "order-by", required = false, defaultValue = "ASC")
                    String sortingDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(sortingDirection), "username");
        Page<UserDto> userPage = userService.findAll(pageable);
        userPage.getContent().forEach(linkCreator::addUserLinks);
        return pagedResourcesAssembler.toModel(userPage);
    }

    /**
     * Gets user by id based on GET request.
     *
     * @param id the identifier
     * @return the user by identifier
     */
    @GetMapping("/{id}")
    @PostAuthorize("#id == authentication.principal.userId or hasAuthority('ADMIN')")
    public UserDto getUserById(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                               @PathVariable("id") long id) {
        UserDto user = userService.findById(id);
        user.setUsername(user.getUsername());
        linkCreator.addUserLinks(user);
        return user;
    }
}
