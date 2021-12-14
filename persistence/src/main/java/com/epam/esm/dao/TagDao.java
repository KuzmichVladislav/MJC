package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The Interface TagDao describes some query methods based on query object construction.
 *
 * @author Vladislav Kuzmich
 */
public interface TagDao extends BaseDao<Tag> {

    /**
     * Find tag entity by name.
     *
     * @param name the name of tag
     * @return the optional of tag entity
     */
    Optional<Tag> findByName(String name);

    /**
     * Find by certificate identifier.
     *
     * @param giftCertificateId the gift certificate identifier
     * @return the list of tags
     */
    List<Tag> findByCertificateId(long giftCertificateId);
}
