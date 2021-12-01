package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

public interface TagDao {

    Tag createTag(Tag tag);

    Tag readTag(Tag tag);

    Tag deleteTag(Tag tag);
}
