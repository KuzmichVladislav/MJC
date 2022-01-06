package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.util.GiftCertificateCriteriaQueryCreator;
import com.epam.esm.util.GiftCertificateQueryCreator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateDaoImpl is the implementation of the {@link GiftCertificateDao} interface.
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String REMOVE_GIFT_CERTIFICATE = "update GiftCertificate gc set gc.isRemoved = true where gc.id = ?1";
    private static final String TOTAL_NUMBER_OF_ITEMS = "select count(gc) from GiftCertificate gc where gc.isRemoved = false";
    private static final String FIND_ALL_GIFT_CERTIFICATES = "select gc from GiftCertificate gc left join gc.tags t";
    private final GiftCertificateQueryCreator giftCertificateQueryCreator;
    private final GiftCertificateCriteriaQueryCreator giftCertificateCriteriaQueryCreator;

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateDaoImpl(GiftCertificateQueryCreator giftCertificateQueryCreator,
                                  GiftCertificateCriteriaQueryCreator giftCertificateCriteriaQueryCreator) {
        this.giftCertificateQueryCreator = giftCertificateQueryCreator;
        this.giftCertificateCriteriaQueryCreator = giftCertificateCriteriaQueryCreator;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(GiftCertificateQueryParameter queryParameter) {
        CriteriaQuery<GiftCertificate> giftCertificateCriteriaQuery = giftCertificateCriteriaQueryCreator.getGiftCertificateCriteriaQuery(queryParameter, entityManager);
        return entityManager.createQuery(giftCertificateCriteriaQuery)
                .setFirstResult(queryParameter.getFirstValue())
                .setMaxResults(queryParameter.getSize())
                .getResultList();
/*
 FIXME: 1/6/2022
        return entityManager.createQuery
                (FIND_ALL_GIFT_CERTIFICATES + giftCertificateQueryCreator.mapRequestParameters(queryParameter),
                        GiftCertificate.class)
                .setFirstResult(queryParameter.getFirstValue())
                .setMaxResults(queryParameter.getSize())
                .getResultList();
*/
    }

    @Override
    public long getTotalNumberOfItems() {
        return (Long) entityManager.createQuery(TOTAL_NUMBER_OF_ITEMS).getSingleResult();
    }

    @Override
    public void remove(GiftCertificate giftCertificate) {
        entityManager.createQuery(REMOVE_GIFT_CERTIFICATE)
                .setParameter(1, giftCertificate.getId()).executeUpdate();
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }
}
