package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag create(Tag tag) {
        int tagId = tagDao.create(tag);
        return tagDao.read(tagId);
    }

    @Override
    public Tag read(int id) {
        Tag tag = tagDao.read(id);
        tag.setGiftCertificateList(giftCertificateService.readAllCertificateByTagId(id));
        return tag;
    }

    @Override
    public List<Tag> readAll() {
        return tagDao.readAll();
    }

    @Override
    public boolean delete(int id) {
        return tagDao.delete(id);
    }

    public void addTagToCertificate(int giftCertificateId, int tagId) {
        tagDao.addTagToCertificate(giftCertificateId, tagId);
    }

    public Optional<Tag> findByName(String name) {
        return tagDao.findByName(name);
    }

    public List<Tag> readAllTagsByCertificateId(int id) {
        return tagDao.readAllTagsByCertificateId(id);
    }
}
