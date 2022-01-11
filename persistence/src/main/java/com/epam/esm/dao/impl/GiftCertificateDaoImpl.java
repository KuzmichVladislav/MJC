package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.util.GiftCertificateCriteriaQueryCreator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateDaoImpl is the implementation of the {@link GiftCertificateDao} interface.
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String REMOVE_GIFT_CERTIFICATE = "update GiftCertificate gc set gc.isRemoved = true where gc.id = ?1";
    private final GiftCertificateCriteriaQueryCreator giftCertificateCriteriaQueryCreator;

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateDaoImpl(GiftCertificateCriteriaQueryCreator giftCertificateCriteriaQueryCreator) {
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
        return entityManager.createQuery(giftCertificateCriteriaQueryCreator
                .getGiftCertificateCriteriaQuery(queryParameter, entityManager))
                .setFirstResult(queryParameter.getFirstValue())
                .setMaxResults(queryParameter.getSize())
                .getResultList();
    }

    @Override
    public long getTotalNumberOfItems(GiftCertificateQueryParameter queryParameter) {
        return entityManager.createQuery(giftCertificateCriteriaQueryCreator
                .getGiftCertificateCriteriaQuery(queryParameter, entityManager))
                .getResultList().size();
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
