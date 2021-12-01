package com.epam.esm.controller;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tag")
public class TagController {

    private final TagDao tagDao;

    @Autowired
    public TagController(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @GetMapping("/{id}")
    public Tag show(@PathVariable("id") int id) {
        return tagDao.show(id);
    }

    @PostMapping()
    public void create(@RequestBody String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagDao.save(tag);
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("giftCertificate") GiftCertificate giftCertificate,
                         @PathVariable("id") int id) {
        // TODO: 11/29/2021 create dao
        //  personDAO.update(id, person);
        return "redirect:/gift_certificate";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        // TODO: 11/29/2021 create dao
        //  personDAO.delete(id);
        return "redirect:/gift_certificate";
    }
}
