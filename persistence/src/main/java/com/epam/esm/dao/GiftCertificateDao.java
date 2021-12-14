package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.RequestSqlParam;

import java.util.List;

/**
 * The Interface GiftCertificateDao describes some query methods based on query object construction.
 *
 * @author Vladislav Kuzmich
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    /**
     * Update gift certificate in database.
     *
     * @param giftCertificate the gift certificate entity
     * @return the gift certificate entity
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Find all gift certificate by tag identifier.
     *
     * @param tagId the tag identifier
     * @return the gift certificate entity
     */
    List<GiftCertificate> findAllCertificateByTagId(long tagId);

    /**
     * Adds tag to certificate.
     *
     * @param giftCertificateId the gift certificate identifier
     * @param tagId             the tag identifier
     */
    void addTagToCertificate(long giftCertificateId, long tagId);

    /**
     * Removes the association between gift certificate and tag.
     *
     * @param giftCertificateId the gift certificate identifier
     */
    void removeFromTableGiftCertificateTagInclude(long giftCertificateId);

    /**
     * Find gift certificate by request parameters.
     *
     * @param requestParam the request parameters
     * @return the list of gift certificates
     */
    List<GiftCertificate> findByParameters(RequestSqlParam requestParam);
}
