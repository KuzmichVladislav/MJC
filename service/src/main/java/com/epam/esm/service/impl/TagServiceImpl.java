package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDaoImpl tagDao;
    GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public TagServiceImpl(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag create(Tag tag) {
        int tagId = tagDao.createTag(tag);
        return tagDao.readTag(tagId);
    }

    @Override
    public Tag read(int id) {
        Tag tag = tagDao.readTag(id);
        tag.setGiftCertificateList(giftCertificateService.readAllCertificateByTagId(id));
        return tag;
    }

    @Override
    public List<Tag> readAll() {
        return tagDao.readAllTags();
    }

    @Override
    public boolean delete(int id) {
        return tagDao.deleteTag(id);
    }

    public void addTagToCertificate(int giftCertificateId, int tagId) {
        tagDao.addTagToCertificate(giftCertificateId, tagId);
    }

    public int findByName(String name) {
        return tagDao.findByName(name);
    }

    public List<Tag> readAllTagsByCertificateId(int id) {
        return tagDao.readAllTagsByCertificateId(id);
    }
}
