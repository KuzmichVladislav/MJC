package com.epam.esm.controller;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
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

import static com.epam.esm.exception.ExceptionKey.ID_MIGHT_NOT_BE_NEGATIVE;
import static com.epam.esm.exception.ExceptionKey.PAGE_MIGHT_NOT_BE_NEGATIVE;
import static com.epam.esm.exception.ExceptionKey.SIZE_MIGHT_NOT_BE_NEGATIVE;

/**
 * The Class TagController is a Rest Controller class which will have
 * all end points for tag which is includes POST, GET, DELETE.
 */
@RestController
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final LinkCreator linkCreator;
    private final TagService tagService;

    /**
     * Get list of tags based on GET request.
     *
     * @param page             the number of page
     * @param size             the size of display items
     * @param sortingDirection the sorting direction
     * @return the all tags
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<TagDto> getAllTags(@Min(value = 1, message = PAGE_MIGHT_NOT_BE_NEGATIVE)
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
        PagedModel<TagDto> tagPage = tagService.findAll(queryParameterDto);
        tagPage.getContent().forEach(linkCreator::addTagLinks);
        linkCreator.addTagPaginationLinks(queryParameterDto, tagPage);
        return tagPage;
    }

    /**
     * Create a new tag based on POST request.
     *
     * @param tagDto the tag DTO
     * @return the tag DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public TagDto addTag(@Valid @RequestBody TagDto tagDto) {
        TagDto resultTag = tagService.add(tagDto);
        linkCreator.addTagLinks(resultTag);
        return resultTag;
    }

    /**
     * Get a tag by identifier based on GET request
     *
     * @param id the tag identifier
     * @return the tag
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TagDto getTagById(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                             @PathVariable("id") long id) {
        TagDto resultTag = tagService.findById(id);
        linkCreator.addTagLinks(resultTag);
        return resultTag;
    }

    /**
     * Delete tag by tag identifier based on DELETE request.
     *
     * @param id the tag identifier
     * @return the http entity
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Void> deleteTag(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                                      @PathVariable("id") long id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find most used tag based on GET request.
     *
     * @param id the identifier
     * @return the tag dto
     */
    @GetMapping("/users/{id}/most-used")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TagDto findMostUsedTag(@Positive(message = ID_MIGHT_NOT_BE_NEGATIVE)
                                  @PathVariable("id") long id) {
        TagDto resultTag = tagService.findMostUsedTag(id);
        linkCreator.addTagLinks(resultTag);
        return resultTag;
    }
}
