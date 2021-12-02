package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDaoImpl tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = (TagDaoImpl) tagDao;
    }

    @Override
    public Tag create(Tag tag) {
        int tagId = tagDao.createTag(tag);
        return tagDao.readTag(tagId);
    }

    @Override
    public Tag read(int id) {
        return tagDao.readTag(id);
    }

    @Override
    public List<Tag> readAll() {
        return tagDao.readAllTags();
    }

    @Override
    public boolean delete(int id) {
        return tagDao.deleteTag(id);
    }
}
