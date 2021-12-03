package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {

    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate findGiftCertificateById(int id);

    List<GiftCertificate> findAllGiftCertificates();

    GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate);

    boolean removeGiftCertificateById(int id);

    List<GiftCertificate> readAllCertificateByTagId(int tagId);

}
