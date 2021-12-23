package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateController is a Rest Controller class which will have
 * all end points for gift certificate which is includes POST, GET, UPDATE, DELETE.
 */
@RestController
@RequestMapping("/v1/gift-certificates")
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        System.out.println(giftCertificateDto);
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
    @GetMapping
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
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificate(@PathVariable("id") String id) {
        return giftCertificateService.findById(id);
    }

    /**
     * Update gift certificate based on PATCH request.
     *
     * @param id                 the gift certificate identifier
     * @param giftCertificateDto giftCertificateDto the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    @PatchMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable("id") String id,
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(id, giftCertificateDto);
    }

    /**
     * Delete gift certificate by gift certificate identifier based on DELETE request.
     *
     * @param id the gift certificate identifier
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable("id") long id) {
        giftCertificateService.removeById(id);
    }
}
