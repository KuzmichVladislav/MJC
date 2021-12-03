package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate read(int id);

    List<GiftCertificate> readAll();

    GiftCertificate update(int id, GiftCertificate giftCertificate);

    boolean delete(int id);
}
