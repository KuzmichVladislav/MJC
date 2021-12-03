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
    public Tag addTag(Tag tag) {
        int tagId = tagDao.addTag(tag).getId();
        return tagDao.findTagById(tagId);
    }

    @Override
    public Tag findTagById(int id) {
        Tag tag = tagDao.findTagById(id);
        tag.setGiftCertificateList(giftCertificateService.findAllCertificateByTagId(id));
        return tag;
    }

    @Override
    public List<Tag> findAllTags() {
        return tagDao.findAllTags();
    }

    @Override
    public boolean removeTagById(int id) {
        return tagDao.removeTagById(id);
    }

    @Override
    public void removeTagByCertificateId(int certificateId) {
        tagDao.removeTagByCertificateId(certificateId);
    }

    public void addTagToCertificate(int giftCertificateId, int tagId) {
        tagDao.addTagToCertificate(giftCertificateId, tagId);
    }

    public Optional<Tag> findByName(String name) {
        return tagDao.findTagByName(name);
    }

    public List<Tag> readAllTagsByCertificateId(int id) {
        return tagDao.readAllTagsByCertificateId(id);
    }
}
