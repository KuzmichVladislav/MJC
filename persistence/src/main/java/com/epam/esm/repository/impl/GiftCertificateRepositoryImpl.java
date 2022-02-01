package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.repository.GiftCertificateRepositoryJpa;
import com.epam.esm.util.GiftCertificateCriteriaQueryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The Class GiftCertificateRepositoryImpl is the implementation of the {@link GiftCertificateRepositoryJpa} interface.
 */
@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepositoryJpa {

    private final GiftCertificateCriteriaQueryCreator giftCertificateCriteriaQueryCreator;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> findAllGiftCertificates(GiftCertificateQueryParameter giftCertificateQueryParameter) {
        return entityManager.createQuery(giftCertificateCriteriaQueryCreator
                .getGiftCertificateCriteriaQuery(giftCertificateQueryParameter, entityManager))
                .setFirstResult(giftCertificateQueryParameter.getFirstValue())
                .setMaxResults(giftCertificateQueryParameter.getSize())
                .getResultList();
    }

    @Override
    public long getTotalNumberOfItems(GiftCertificateQueryParameter giftCertificateQueryParameter) {
        return entityManager.createQuery(giftCertificateCriteriaQueryCreator
                .getGiftCertificateCriteriaQuery(giftCertificateQueryParameter, entityManager))
                .getResultList().size();
    }
}
