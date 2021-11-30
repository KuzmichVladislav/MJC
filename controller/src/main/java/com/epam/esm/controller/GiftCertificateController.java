package com.epam.esm.controller;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gift_certificate")
public class GiftCertificateController {

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateController(GiftCertificateDao giftCertificateDao) {
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
    public void create(@ModelAttribute("tag")Tag tag) {
        System.out.println("work");
//        Tag tag = new Tag();
//        tag.setId(id);
//        tag.setName(name);
        giftCertificateDao.save(tag);
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
