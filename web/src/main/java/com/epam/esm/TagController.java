package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.add(tag);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tag getTagById(@PathVariable("id") long id) {
        return tagService.findById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTag(@PathVariable("id") long id) {
        return tagService.removeById(id);
    }

    // TODO: 12/1/2021
//    @GetMapping(value = "/hello", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Tag> getColumnsModel() {
//        return new ResponseEntity<>(new Tag(1) , HttpStatus.OK);
//    }
}