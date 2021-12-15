package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateController is a Rest Controller class which will have
 * all end points for gift certificate which is includes POST, GET, UPDATE, DELETE.
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
     * Create a new gift certificate based on POST request.
     *
     * @param giftCertificateDto the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    /**
     * Get list of gift certificates based on GET request.
     *
     * @param name        the name parameter
     * @param description the description parameter
     * @param tagName     the tag name parameter
     * @param sortType    the sort type parameter
     * @param sortOrder   the sort order parameter
     * @return all gift certificates
     */
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GiftCertificateDto> getAllGiftCertificates
    (@RequestParam(value = "name", required = false) Optional<String> name,
     @RequestParam(value = "description", required = false) Optional<String> description,
     @RequestParam(value = "tag-name", required = false) Optional<String> tagName,
     @RequestParam(value = "sort", required = false) Optional<List<String>> sortType,
     @RequestParam(value = "order-by", required = false) Optional<String> sortOrder) {
        GiftCertificateQueryParameterDto queryParameterDto = GiftCertificateQueryParameterDto.builder()
                .name(name)
                .tagName(tagName)
                .description(description)
                .sortType(sortType)
                .sortOrder(sortOrder)
                .build();
        return giftCertificateService.findByParameters(queryParameterDto);
    }

    /**
     * Get a gift certificate by identifier based on GET request
     *
     * @param id the gift certificate identifier
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
     * Delete gift certificate by gift certificate identifier based on DELETE request.
     *
     * @param id the gift certificate identifier
     * @return no content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteGiftCertificate(@PathVariable("id") long id) {
        return giftCertificateService.removeById(id);
    }
}
