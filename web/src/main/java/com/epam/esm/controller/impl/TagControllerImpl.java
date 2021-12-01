package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagControllerImpl implements TagController {

    private final TagDao tagDao;

    @Autowired
    public TagControllerImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tag create(@RequestBody Tag tag) {
        return tagDao.createTag(tag);
    }

    @Override
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tag read(@PathVariable("id") int id) {
        return tagDao.readTag(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return tagDao.deleteTag(id);
    }

    // TODO: 12/1/2021
//    @GetMapping(value = "/hello", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Tag> getColumnsModel() {
//        return new ResponseEntity<>(new Tag(1) , HttpStatus.OK);
//    }
}
