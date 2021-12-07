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

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag add(Tag tag) {
        return tagDao.add(tag);
    }

    @Override
    public Tag findById(long id) {
        return tagDao.findById(id).orElse(null);// TODO: 12/7/2021
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public boolean removeById(long id) {
        return tagDao.removeById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findTagByName(name);
    }
}
