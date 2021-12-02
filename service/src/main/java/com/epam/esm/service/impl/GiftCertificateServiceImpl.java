package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
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

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        List<Tag> tagList = giftCertificate.getTagList();
        List<Tag> existingTags = tagService.readAll();
        int giftCertificateId = giftCertificateDao.create(giftCertificate);
        tagList.stream()
                .distinct()
                .filter(e -> !existingTags.contains(e))
                .forEach(t -> {
                    try {
                        tagService.addTagToCertificate(giftCertificateId, tagService.create(t).getId());
                    } catch (DuplicateKeyException e) {
                        tagService.addTagToCertificate(giftCertificateId, tagService.findByName(t.getName()));
                    }
                });
        return giftCertificateDao.read(giftCertificateId);
    }

    @Override
    public GiftCertificate read(int id) {
        GiftCertificate giftCertificate = giftCertificateDao.read(id);
        giftCertificate.setTagList(tagService.readAllTagsByCertificateId(id));
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(int id, GiftCertificate giftCertificate) {
        return giftCertificateDao.update(id, giftCertificate);
    }

    @Override
    public boolean delete(int id) {
        return giftCertificateDao.delete(id);
    }

    public List<GiftCertificate> readAllCertificateByTagId(int giftCertificateId) {
        return giftCertificateDao.readAllCertificateByTagId(giftCertificateId);
    }
}
