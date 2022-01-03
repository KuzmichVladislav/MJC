package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryParameter;

import java.util.List;

/**
 * The Interface GiftCertificateDao describes some query methods based on query object construction.
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    /**
     * Update gift certificate in database.
     *
     * @param giftCertificate the gift certificate entity
     */
    void update(GiftCertificate giftCertificate);
}
