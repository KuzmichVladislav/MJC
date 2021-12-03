package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {

    Tag addTag(Tag tag);

    List<Tag> findAllTags();

    Tag findTagById(int id);

    boolean removeTagById(int id);

    void removeTagByCertificateId(int certificateId);
}
