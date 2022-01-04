package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.util.GiftCertificateQueryCreator;
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

    private static final String REMOVE_GIFT_CERTIFICATE = "update GiftCertificate gc set gc.isRemoved = true where gc.id = ?1";
    private static final String TOTAL_NUMBER_OF_ITEMS = "select count(gc) from GiftCertificate gc";
    private static final String FIND_ALL_GIFT_CERTIFICATES = "select gc from GiftCertificate gc left join gc.tags t";
    private final GiftCertificateQueryCreator giftCertificateQueryCreator;

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateDaoImpl(GiftCertificateQueryCreator giftCertificateQueryCreator) {
        this.giftCertificateQueryCreator = giftCertificateQueryCreator;
    }

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
    public List<GiftCertificate> findAll(QueryParameter queryParameter) {
        // TODO: 1/3/2022 fix removed
        String qlString = FIND_ALL_GIFT_CERTIFICATES + giftCertificateQueryCreator.mapRequestParameters(queryParameter);
        System.out.println(qlString);
        return entityManager.createQuery
                (qlString,
                        GiftCertificate.class)
                .setFirstResult(queryParameter.getFirstValue())
                .setMaxResults(queryParameter.getSize())
                .getResultList();
    }

    @Override
    public long getTotalNumberOfItems() {
        return (Long) entityManager.createQuery(TOTAL_NUMBER_OF_ITEMS).getSingleResult();
    }

    @Override
    @Transactional
    public boolean remove(GiftCertificate giftCertificate) {
        int result = entityManager.createQuery(REMOVE_GIFT_CERTIFICATE)
                .setParameter(1, giftCertificate.getId()).executeUpdate();
        return result > 0;
    }

    @Override
    @Transactional
    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }
}
