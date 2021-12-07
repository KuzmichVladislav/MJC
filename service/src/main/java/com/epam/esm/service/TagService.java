package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService extends BaseService<Tag> {

    Optional<Tag> findByName(String name);

    List<Tag> readAllTagsByCertificateId(long giftCertificateId);
}
