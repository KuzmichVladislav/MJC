package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate findGiftCertificateById(int id);

    List<GiftCertificate> findAllGiftCertificates();

    GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate);

    boolean removeGiftCertificateById(int id);
}
