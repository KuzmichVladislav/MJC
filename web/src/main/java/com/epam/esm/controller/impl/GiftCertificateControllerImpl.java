package com.epam.esm.controller.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gift_certificate")
public class GiftCertificateControllerImpl implements GiftCertificateController {

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateControllerImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate create(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateDao.createGiftCertificate(giftCertificate);
    }

    @Override
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate read(@PathVariable("id") int id) {
        return giftCertificateDao.readGiftCertificate(id);
    }

    @Override
    @PatchMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate update(@PathVariable("id") int id, GiftCertificate giftCertificate) {
        return giftCertificateDao.updateGiftCertificate(id, giftCertificate);
    }

    @Override
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return giftCertificateDao.deleteGiftCertificate(id);
    }
}
