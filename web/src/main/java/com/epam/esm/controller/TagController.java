package com.epam.esm.controller;

import com.epam.esm.dto.ApplicationPageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The Class TagController is a Rest Controller class which will have
 * all end points for tag which is includes POST, GET, DELETE.
 */
@RestController
@RequestMapping("/v1/tags")
public class TagController {

    private final TagService tagService;

    /**
     * Instantiates a new tag controller.
     *
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Get list of tags users based on GET request.
     *
     * @return the all tags
     */
    @GetMapping(params = { "page", "size" })
    public List<TagDto> getAllTags(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        List<TagDto> tags = tagService.findAll(page, size);
                tags.forEach(t -> addLinks(String.valueOf(t.getId()), t));
        return tags;
    }

    /**
     * Create a new tag based on POST request.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        TagDto resultTag = tagService.add(tagDto);
        addLinks(String.valueOf(resultTag.getId()), resultTag);
        return resultTag;
    }

    /**
     * Get a tag by identifier based on GET request
     *
     * @param id the tag identifier
     * @return the tag
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable("id") String id) {
        TagDto resultTag = tagService.findById(id);
        addLinks(id, resultTag);
        return resultTag;
    }

    /**
     * Delete tag by tag identifier based on DELETE request.
     *
     * @param id the tag identifier
     */
    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteTag(@PathVariable("id") String id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addLinks(String id, TagDto resultTag) {
        resultTag.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        resultTag.add(linkTo(methodOn(TagController.class).deleteTag(id)).withRel("delete"));
    }
}
