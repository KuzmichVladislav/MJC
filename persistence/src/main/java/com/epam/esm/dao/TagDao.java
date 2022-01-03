package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * The Interface TagDao describes some query methods based on query object construction.
 */
public interface TagDao extends BaseDao<Tag> {

    /**
     * Find tag entity by name.
     *
     * @param name the name of tag
     * @return the optional of tag entity
     */
    Optional<Tag> findByName(String name);

    // TODO: 12/28/2021 add JD
    Optional<Tag> findMostUsedTag(long id);
}
