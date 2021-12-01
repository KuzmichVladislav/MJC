package com.epam.esm.controller;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gift_certificate")
public class GiftCertificateController {

    private final TagDaoImpl giftCertificateDao;

    @Autowired
    public GiftCertificateController(TagDaoImpl giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @GetMapping()
    public String index() {
        return "gift_certificate/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id) {
        return "gift_certificate/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("gift_certificate") GiftCertificate giftCertificate) {
        return "gift_certificate/new";
    }

    @PostMapping()
    public void create(@RequestBody String name) {
        System.out.println("work");
        Tag tag = new Tag();
//        tag.setId(id);
       // tag.setName(name);
        giftCertificateDao.createTag(tag);
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        // TODO: 11/29/2021 create dao
        //  model.addAttribute("person", personDAO.show(id));
        return "gift_certificate/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") GiftCertificate giftCertificate,
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
