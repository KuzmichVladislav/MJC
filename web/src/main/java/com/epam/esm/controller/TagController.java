package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Class TagController is a Rest Controller class which will have
 * all end points for tag which is includes POST, GET, GET ALL, DELETE.
 *
 * @author Vladislav Kuzmich
 */
@RestController
@RequestMapping("/tags")
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
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagDto> getAllTags() {
        return tagService.findAll();
    }

    /**
     * Create a new tag by POST request, end point is http://hostname:port/tags
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.add(tagDto);
    }

    /**
     * Get a tag by id in GET request, end point is http://hostname:port/tags/id
     *
     * @param id the tag id
     * @return the tag
     */
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TagDto getTagById(@PathVariable("id") long id) {
        return tagService.findById(id);
    }

    /**
     * Delete tag.
     *
     * @param id the tag id
     * @return no content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteTag(@PathVariable("id") long id) {
        return tagService.removeById(id);
    }
}
