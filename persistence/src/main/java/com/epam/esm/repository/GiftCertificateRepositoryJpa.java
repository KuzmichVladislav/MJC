package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;

import java.util.List;

/**
 * The interface to expand the capabilities of the {@link GiftCertificateRepository}.
 */
public interface GiftCertificateRepositoryJpa {

    /**
     * Find all gift certificates list.
     *
     * @param giftCertificateQueryParameter the gift certificate query parameter
     * @return the list
     */
    List<GiftCertificate> findAllGiftCertificates(GiftCertificateQueryParameter giftCertificateQueryParameter);

    /**
     * Gets total number of items.
     *
     * @param giftCertificateQueryParameter the gift certificate query parameter
     * @return the total number of items
     */
    long getTotalNumberOfItems(GiftCertificateQueryParameter giftCertificateQueryParameter);
}
