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

/**
 * The Class GiftCertificateController is a Rest Controller class which will have
 * all end points for gift certificate which is includes POST, GET, GET ALL, UPDATE, DELETE.
 *
 * @author Vladislav Kuzmich
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Create a new gift certificate by POST request, end point is http://hostname:port/gift-certificates
     *
     * @param giftCertificateDto the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    /**
     * Get list of gift certificates users based on GET request.
     *
     * @param name        the name
     * @param description the description
     * @param tagName     the tag name
     * @param sort        the sort
     * @param orderBy     the order by
     * @return all gift certificates
     */
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

    /**
     * Get a gift certificate by id in GET request, end point is http://hostname:port/gift-certificates/id
     *
     * @param id the gift certificate id
     * @return the gift certificate
     */
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto getGiftCertificate(@PathVariable("id") long id) {
        return giftCertificateService.findById(id);
    }

    /**
     * Update gift certificate based on PATCH request.
     *
     * @param giftCertificateDto the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    @PatchMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto);
    }

    /**
     * Delete gift certificate based on gift certificate ID.
     *
     * @param id the gift certificate id
     * @return no content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteGiftCertificate(@PathVariable("id") long id) {
        return giftCertificateService.removeById(id);
    }
}
