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
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        List<Tag> tagList = giftCertificate.getTagList();
        List<Tag> existingTags = tagService.findAllTags();
        int giftCertificateId = giftCertificateDao.addGiftCertificate(giftCertificate).getId();
        tagList.stream()
                .distinct()
                .filter(e -> !existingTags.contains(e))
                .forEach(t -> {
                    Optional<Tag> foundTag = tagService.findByName(t.getName());
                    if (foundTag.isPresent()) {
                        tagService.addTagToCertificate(giftCertificateId, foundTag.get().getId());
                    } else {
                        tagService.addTagToCertificate(giftCertificateId, tagService.addTag(t).getId());
                    }

                });
        return giftCertificateDao.findGiftCertificateById(giftCertificateId);
    }

    @Override
    public GiftCertificate findGiftCertificateById(int id) {
        GiftCertificate giftCertificate = giftCertificateDao.findGiftCertificateById(id);
        giftCertificate.setTagList(tagService.readAllTagsByCertificateId(id));
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificates() {
        return giftCertificateDao.findAllGiftCertificates();
    }

    @Override
    public GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate) {
        return giftCertificateDao.updateGiftCertificate(id, giftCertificate);
    }

    @Override
    public boolean removeGiftCertificateById(int id) {
        return giftCertificateDao.removeGiftCertificateById(id);
    }

    public List<GiftCertificate> readAllCertificateByTagId(int giftCertificateId) {
        return giftCertificateDao.readAllCertificateByTagId(giftCertificateId);
    }
}
