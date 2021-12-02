package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {

    int create(GiftCertificate giftCertificate);

    GiftCertificate read(int id);

    GiftCertificate update(int id, GiftCertificate giftCertificate);

    boolean delete(int id);

    List<GiftCertificate> readAllCertificateByTagId(int tagId);

}
