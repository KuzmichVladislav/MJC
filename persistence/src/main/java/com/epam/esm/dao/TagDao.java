package com.epam.esm.dao;

import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * The Interface TagDao describes some query methods based on query object construction.
 */
public interface TagDao extends BaseDao<Tag, QueryParameter> {

    /**
     * Find tag entity by name.
     *
     * @param name the name of tag
     * @return the optional of tag entity
     */
    Optional<Tag> findByName(String name);

    /**
     * Get the most widely used tag of a user with the highest cost of all orders.
     *
     * @param id the identifier
     * @return the optional
     */
    Optional<Tag> findMostUsedTag(long id);

    /**
     * Is part of gift certificate.
     *
     * @param id the identifier
     * @return true if the tag is part of gift certificate
     */
    boolean isPartOfGiftCertificate(long id);
}
