package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GiftCertificateDto> getAllGiftCertificates() {
        return giftCertificateService.findAll();
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto getGiftCertificate(@PathVariable("id") long id) {

        GiftCertificateDto byId = giftCertificateService.findById(id);
        System.out.println(byId.getCreateDate());
        return byId;
    }

    @PatchMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGiftCertificate(@PathVariable("id") long id) {
        return giftCertificateService.removeById(id);
    }

    @GetMapping(value = "/hello", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getGiftCertificate() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

}
