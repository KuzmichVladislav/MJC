package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

/**
 * The Interface GiftCertificateService.
 * A interface to define all required methods for gift certificate DTO object.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    /**
     * Update gift certificate DTO object.
     *
     * @param id              the gift certificate identifier
     * @param giftCertificate the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    GiftCertificateDto update(long id, GiftCertificateDto giftCertificate);
}
