package com.epam.esm.controller.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift_certificate")
public class GiftCertificateControllerImpl implements GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateControllerImpl(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate create(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.addGiftCertificate(giftCertificate);
    }

    @Override
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate read(@PathVariable("id") int id) {
        return giftCertificateService.findGiftCertificateById(id);
    }

    @Override
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GiftCertificate> readAll() {
        return giftCertificateService.findAllGiftCertificates();
    }

    @Override
    @PatchMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate update(@PathVariable("id") int id, @RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.updateGiftCertificate(id, giftCertificate);
    }

    @Override
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return giftCertificateService.removeGiftCertificateById(id);
    }
}
