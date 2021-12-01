package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateService {

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate read(int id);

    GiftCertificate update(int id, GiftCertificate giftCertificate);

    boolean delete(int id);
}
