package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import org.springframework.hateoas.PagedModel;

/**
 * The Interface GiftCertificateService.
 * An interface to define all required methods for gift certificate DTO object.
 */
public interface GiftCertificateService {

    /**
     * Adds the gift certificate DTO object.
     *
     * @param giftCertificateDto the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    GiftCertificateDto add(GiftCertificateDto giftCertificateDto);

    /**
     * Find by gift certificate DTO object identifier.
     *
     * @param id the gift certificate DTO object identifier
     * @return the gift certificate DTO object
     */
    GiftCertificateDto findById(long id);

    /**
     * Update gift certificate DTO object.
     *
     * @param id              the gift certificate DTO object identifier
     * @param giftCertificate the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    GiftCertificateDto update(long id, GiftCertificateDto giftCertificate);

    /**
     * Removes the by gift certificate DTO object identifier.
     *
     * @param id the gift certificate DTO object identifier
     */
    void removeById(long id);

    /**
     * Find all paged model.
     *
     * @param giftCertificateQueryParameterDto the gift certificate query parameter dto
     * @return the paged model
     */
    PagedModel<GiftCertificateDto> findAll(GiftCertificateQueryParameterDto giftCertificateQueryParameterDto);
}
