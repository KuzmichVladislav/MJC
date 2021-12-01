package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        return giftCertificateDao.createGiftCertificate(giftCertificate);
    }

    @Override
    public GiftCertificate read(int id) {
        return giftCertificateDao.readGiftCertificate(id);
    }

    @Override
    public GiftCertificate update(int id, GiftCertificate giftCertificate) {
        return giftCertificateDao.updateGiftCertificate(id, giftCertificate);
    }

    @Override
    public boolean delete(int id) {
        return giftCertificateDao.deleteGiftCertificate(id);
    }
}
