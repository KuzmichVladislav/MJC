package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.RequestSqlParamDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gift-certificates")
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
    public List<GiftCertificateDto> getAllGiftCertificates
            (@RequestParam(value = "name", required = false) Optional<String> name,
             @RequestParam(value = "description", required = false) Optional<String> description,
             @RequestParam(value = "tag-name", required = false) Optional<String> tagName,
             @RequestParam(value = "sort", required = false) Optional<List<String>> sort,
             @RequestParam(value = "order-by", required = false) Optional<String> orderBy) {
        RequestSqlParamDto requestParams = RequestSqlParamDto.builder()
                .name(name)
                .tagName(tagName)
                .description(description)
                .sort(sort)
                .orderBy(orderBy)
                .build();
        return giftCertificateService.findByParameters(requestParams);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto getGiftCertificate(@PathVariable("id") long id) {
        return giftCertificateService.findById(id);
    }

    @PatchMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteGiftCertificate(@PathVariable("id") long id) {
        return giftCertificateService.removeById(id);
    }
}
