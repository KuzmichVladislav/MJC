package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.dto.PageWrapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.LinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateController is a Rest Controller class which will have
 * all end points for gift certificate which is includes POST, GET, UPDATE, DELETE.
 */
@RestController
@RequestMapping("/v1/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final LinkCreator linkCreator;

    /**
     * Create a new gift certificate based on POST request.
     *
     * @param giftCertificateDto the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto resultGiftCertificate = giftCertificateService.add(giftCertificateDto);
        linkCreator.addGiftCertificateLinks(resultGiftCertificate);
        return resultGiftCertificate;
    }

    /**
     * Gets all gift certificates based on GET request.
     *
     * @param page             the number of page
     * @param size             the size of display items
     * @param name             the name of gift certificate
     * @param description      the description  of gift certificate
     * @param tagNames         the tag names
     * @param sortParameter    the sort parameter
     * @param sortingDirection the sorting direction
     * @return the all gift certificates
     */
    @GetMapping
    public PageWrapper<GiftCertificateDto>
    getAllGiftCertificates(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                           @RequestParam(value = "name", required = false) Optional<String> name,
                           @RequestParam(value = "description", required = false) Optional<String> description,
                           @RequestParam(value = "tag-name", required = false) Optional<List<String>> tagNames,
                           @RequestParam(value = "sort", required = false, defaultValue = "NAME")
                                   GiftCertificateQueryParameterDto.SortParameter sortParameter,
                           @RequestParam(value = "order-by", required = false, defaultValue = "ASC")
                                   GiftCertificateQueryParameterDto.SortingDirection sortingDirection) {
        GiftCertificateQueryParameterDto giftCertificateQueryParameterDto = GiftCertificateQueryParameterDto.giftCertificateQueryParameterDtoBuilder()
                .name(name)
                .description(description)
                .tagNames(tagNames)
                .sortParameter(sortParameter)
                .page(page)
                .size(size)
                .sortingDirection(sortingDirection)
                .build();
        PageWrapper<GiftCertificateDto> giftCertificatePage = giftCertificateService.findAll(giftCertificateQueryParameterDto);
        giftCertificatePage.getItemsPerPage().forEach(linkCreator::addGiftCertificateLinks);
        return giftCertificatePage;
    }

    /**
     * Get a gift certificate by identifier based on GET request
     *
     * @param id the gift certificate identifier
     * @return the gift certificate
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable("id") @Min(1) long id) {
        GiftCertificateDto resultGiftCertificate = giftCertificateService.findById(id);
        linkCreator.addGiftCertificateLinks(resultGiftCertificate);
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
    public GiftCertificateDto updateGiftCertificate(@PathVariable("id") @Min(1) long id,
                                                    @Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto resultGiftCertificate = giftCertificateService.update(id, giftCertificateDto);
        linkCreator.addGiftCertificateLinks(resultGiftCertificate);
        return resultGiftCertificate;
    }

    /**
     * Delete gift certificate by gift certificate identifier based on DELETE request.
     *
     * @param id the gift certificate identifier
     * @return the http entity
     */
    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteGiftCertificate(@PathVariable("id") @Min(1) long id) {
        giftCertificateService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
