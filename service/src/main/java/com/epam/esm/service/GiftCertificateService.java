package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;

import java.util.List;

/**
 * The Interface GiftCertificateService.
 * A interface to define all required methods for gift certificate DTO object.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    /**
     * Update gift certificate DTO object.
     *
     * @param giftCertificate the gift certificate DTO object
     * @return the gift certificate DTO object
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    /**
     * Find gift certificate DTO object request by parameters.
     *
     * @param requestParameter the request parameters
     * @return the list of gift certificate DTO object
     */
    List<GiftCertificateDto> findByParameters(GiftCertificateQueryParameterDto requestParameter);
}
