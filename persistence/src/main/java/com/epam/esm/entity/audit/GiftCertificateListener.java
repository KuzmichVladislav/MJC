package com.epam.esm.entity.audit;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class GiftCertificateListener {

    @PrePersist
    public void createdOn(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void updateOn(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }
}
