package com.epam.esm.controller;

import com.epam.esm.entity.Tag;

public interface TagController {

    Tag create(Tag tag);

    Tag read(int id);

    boolean delete(int id);
}
