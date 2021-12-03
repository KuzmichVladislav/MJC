package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    int create(Tag tag);

    List<Tag> readAll();

    Tag read(int id);

    boolean delete(int id);

    void addTagToCertificate(int giftCertificate, int tag);

    Optional<Tag> findByName(String name);

    List<Tag> readAllTagsByCertificateId(int giftCertificateId);
}
