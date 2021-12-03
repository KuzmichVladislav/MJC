package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateController {

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate read(int id);

    List<GiftCertificate> readAll();

    GiftCertificate update(int id, GiftCertificate giftCertificate);

    boolean delete(int id);
}
