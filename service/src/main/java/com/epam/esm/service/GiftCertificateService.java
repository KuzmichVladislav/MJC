package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;

/**
 * The Interface GiftCertificateService.
 * A interface to define all required methods for gift certificate DTO object.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto, GiftCertificateQueryParameterDto> {

    /**
     * Update gift certificate DTO object.
     *
     * @param id              the gift certificate identifier
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
}
