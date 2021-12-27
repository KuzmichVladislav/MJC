package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        GiftCertificateDto resultGiftCertificate = giftCertificateService.add(giftCertificateDto);
        addLinks(resultGiftCertificate);
        return resultGiftCertificate;
    }

/*
 TODO: 12/24/2021
    /**
     * Get list of gift certificates based on GET request.
     *
     * @param name        the name parameter
     * @param description the description parameter
     * @param tagName     the tag name parameter
     * @param sortType    the sort type parameter
     * @param sortOrder   the sort order parameter
     * @return all gift certificates
     * /
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
*/

    @GetMapping
    public HttpEntity<List<GiftCertificateDto>> getAllGiftCertificates() {
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateService.findAll();
        giftCertificateDtos.forEach(g -> g.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(String.valueOf(g.getId()))).withSelfRel()));
        return new ResponseEntity<>(giftCertificateDtos, HttpStatus.OK);
    }

    /**
     * Get a gift certificate by identifier based on GET request
     *
     * @param id the gift certificate identifier
     * @return the gift certificate
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable("id") String id) {
        GiftCertificateDto resultGiftCertificate = giftCertificateService.findById(id);
        addLinks(resultGiftCertificate);
        return resultGiftCertificate;
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
        GiftCertificateDto resultGiftCertificate = giftCertificateService.update(id, giftCertificateDto);
        addLinks(resultGiftCertificate);
        return resultGiftCertificate;
    }

    /**
     * Delete gift certificate by gift certificate identifier based on DELETE request.
     *
     * @param id the gift certificate identifier
     */
    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteGiftCertificate(@PathVariable("id") String id) {
        giftCertificateService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addLinks(GiftCertificateDto resultGiftCertificate) {
        resultGiftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(String.valueOf(resultGiftCertificate.getId()))).withSelfRel());
        resultGiftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificate(String.valueOf(resultGiftCertificate.getId()), resultGiftCertificate)).withRel("update"));
        resultGiftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteGiftCertificate(String.valueOf(resultGiftCertificate.getId()))).withRel("delete"));
        List<TagDto> tags = resultGiftCertificate.getTags();
        if (tags != null) {
            tags.forEach(t -> {
                t.add(linkTo(methodOn(TagController.class).getTagById(String.valueOf(t.getId()))).withSelfRel());
                t.add(linkTo(methodOn(TagController.class).deleteTag(String.valueOf(t.getId()))).withRel("delete"));
            });
        }
    }
}
