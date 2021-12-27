package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateDaoImpl is the implementation of the {@link GiftCertificateDao} interface.
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String FIND_ALL_GIFT_CERTIFICATES = "select gc from GiftCertificate gc where gc.isRemoved = false";
    private static final String REMOVE_GIFT_CERTIFICATE = "update GiftCertificate gc set gc.isRemoved = true where gc.id = ?1";

/*
    private static final String FIND_CERTIFICATES_BY_PARAMETERS =
            "SELECT DISTINCT gift_certificate.id,\n" +
                    "                gift_certificate.name,\n" +
                    "                gift_certificate.description,\n" +
                    "                gift_certificate.price,\n" +
                    "                gift_certificate.duration,\n" +
                    "                gift_certificate.create_date,\n" +
                    "                gift_certificate.last_update_date\n" +
                    "FROM gift_certificate\n" +
                    "         LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.gift_certificate_id\n" +
                    "         LEFT JOIN tag t on t.id = gcti.tag_id\n";
*/

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll() {
        return entityManager.createQuery(FIND_ALL_GIFT_CERTIFICATES, GiftCertificate.class)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean remove(GiftCertificate giftCertificate) {
        entityManager.createQuery(REMOVE_GIFT_CERTIFICATE)
                .setParameter(1, giftCertificate.getId()).executeUpdate();
        return false;        // FIXME: 12/24/2021 return
    }

    @Override
    @Transactional
    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findByParameters(GiftCertificateQueryParameter requestParameter) {
        return null;// TODO: 12/24/2021
/*
        String sqlQuery = FIND_CERTIFICATES_BY_PARAMETERS +
                giftCertificateQueryCreator.mapRequestParameters(requestParameter);
        return jdbcTemplate.query(sqlQuery, giftCertificateMapper);
*/
    }
}
