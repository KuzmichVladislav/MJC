package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateController {

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate read(int id);

    GiftCertificate update(int id, GiftCertificate giftCertificate);

    boolean delete(int id);
}
