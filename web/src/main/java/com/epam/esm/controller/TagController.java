package com.epam.esm.controller;

import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.LinkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * The Class TagController is a Rest Controller class which will have
 * all end points for tag which is includes POST, GET, DELETE.
 */
@RestController
@RequestMapping("/v1/tags")
public class TagController {

    private final LinkCreator linkCreator;
    private final TagService tagService;

    /**
     * Instantiates a new tag controller.
     *
     * @param tagService  the tag service
     * @param linkCreator
     */
    @Autowired
    public TagController(TagService tagService,
                         LinkCreator linkCreator) {
        this.tagService = tagService;
        this.linkCreator = linkCreator;
    }

    /**
     * Get list of tags users based on GET request.
     *
     * @return the all tags
     */
    @GetMapping
    public PageWrapper<TagDto> getAllTags(@RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size,
                                          @RequestParam(value = "order-by", required = false, defaultValue = "ASC") QueryParameterDto.SortingDirection sortingDirection) {
        QueryParameterDto queryParameterDto = QueryParameterDto.builder()
                .page(page)
                .size(size)
                .sortingDirection(sortingDirection)
                .build();
        PageWrapper<TagDto> tagPage = tagService.findAll(queryParameterDto);
        tagPage.getPageValues().forEach(linkCreator::addTagLinks);
        return tagPage;
    }

    /**
     * Create a new tag based on POST request.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public TagDto getTagById(@PathVariable("id") @Min(1) long id) {
        TagDto resultTag = tagService.findById(id);
        linkCreator.addTagLinks(resultTag);
        return resultTag;
    }

    /**
     * Delete tag by tag identifier based on DELETE request.
     *
     * @param id the tag identifier
     */
    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteTag(@PathVariable("id") @Min(1) long id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{id}/most")
    public TagDto findMostUsedTag(@PathVariable("id") @Min(1) long id) {
        TagDto resultTag = tagService.findMostUsedTag(id);
        linkCreator.addTagLinks(resultTag);
        return resultTag;
    }
}
