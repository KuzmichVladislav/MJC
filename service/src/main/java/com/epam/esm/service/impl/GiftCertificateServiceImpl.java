package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDaoImpl tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = (TagDaoImpl) tagDao;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        List<Tag> tagList = giftCertificate.getTagList();
        List<Tag> existingTags = tagDao.readAllTags();
        int giftCertificateId = giftCertificateDao.createGiftCertificate(giftCertificate);
        tagList.stream()
                .distinct()
                .filter(e -> !existingTags.contains(e))
                .forEach(t -> {
                    try {
                        tagDao.addTagToCertificate(giftCertificateId, tagDao.createTag(t));
                    } catch (DuplicateKeyException e) {
                        tagDao.addTagToCertificate(giftCertificateId, tagDao.findByName(t.getName()));
                    }
                });
        return giftCertificateDao.readGiftCertificate(giftCertificateId);
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
