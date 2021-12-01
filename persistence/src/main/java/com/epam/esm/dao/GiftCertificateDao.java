package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDao {

    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate readGiftCertificate(int id);

    GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate);

    boolean deleteGiftCertificate(int id);
}
