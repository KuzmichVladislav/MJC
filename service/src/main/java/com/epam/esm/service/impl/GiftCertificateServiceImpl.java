package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                    Optional<Tag> foundTag = tagService.findByName(t.getName());
                    if (foundTag.isPresent()) {
                        tagService.addTagToCertificate(giftCertificateId, foundTag.get().getId());
                    } else {
                        tagService.addTagToCertificate(giftCertificateId, tagService.create(t).getId());
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
    public List<GiftCertificate> readAll() {
        return giftCertificateDao.readAll();
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
