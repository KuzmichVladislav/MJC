package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    Tag addTag(Tag tag);

    List<Tag> findAllTags();

    Tag findTagById(int id);

    boolean removeTagById(int id);

    void addTagToCertificate(int giftCertificate, int tag);

    Optional<Tag> findTagByName(String name);

    List<Tag> readAllTagsByCertificateId(int giftCertificateId);

    void removeTagByCertificateId(int certificateId);
}
