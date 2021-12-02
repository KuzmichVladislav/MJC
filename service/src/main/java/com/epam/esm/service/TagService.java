package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {

    Tag create(Tag tag);

    Tag read(int id);

    List<Tag> readAll();

    boolean delete(int id);
}
