package com.epam.esm.entity.audit;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * The type Gift certificate listener serves to track changes in the gift certificate table in the database.
 */
public class GiftCertificateListener {

    /**
     * Populates the create and update fields when adding a gift certificate entry to the database.
     *
     * @param giftCertificate the gift certificate entity
     */
    @PrePersist
    public void createdOn(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

    /**
     * Populates the update field when a gift certificate entry in the database changes
     *
     * @param giftCertificate the gift certificate entity
     */
    @PreUpdate
    public void updateOn(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }
}
