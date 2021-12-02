package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {

    int createGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate readGiftCertificate(int id);

    GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate);

    boolean deleteGiftCertificate(int id);
}
