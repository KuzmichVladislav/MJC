package com.epam.esm.service;

import com.epam.esm.entity.Tag;

public interface TagService {

    Tag create(Tag tag);

    Tag read(int id);

    boolean delete(int id);
}
