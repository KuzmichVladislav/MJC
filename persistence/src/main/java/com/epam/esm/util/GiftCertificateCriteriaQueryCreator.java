package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.entity.QueryParameter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateCriteriaQueryCreator {

    private static final String TAGS = "tags";
    private static final String IS_REMOVED = "isRemoved";
    private static final String NAME = "name";
    private static final String LIKE = "%%%s%%";
    private static final String DESCRIPTION = "description";

    public CriteriaQuery<GiftCertificate> getGiftCertificateCriteriaQuery(GiftCertificateQueryParameter queryParameter, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> certificateCriteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = certificateCriteriaQuery.from(GiftCertificate.class);
        Join<Object, Object> tagRoot = giftCertificateRoot.join(TAGS);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isFalse(giftCertificateRoot.get(IS_REMOVED)));
        if (queryParameter.getName().isPresent()) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(NAME), String.format(LIKE, queryParameter.getName().get())));
        }
        if (queryParameter.getDescription().isPresent()) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(DESCRIPTION), String.format(LIKE, queryParameter.getDescription().get())));
        }
        if (queryParameter.getTagNames().isPresent()) {
            queryParameter.getTagNames().get().forEach(tag -> predicates.add(criteriaBuilder.like(tagRoot.get(NAME), tag)));
        }
        Predicate[] arr = new Predicate[predicates.size()];
        arr = predicates.toArray(arr);
        certificateCriteriaQuery.select(giftCertificateRoot).where(arr);
        switch (queryParameter.getSortingDirection()) {
            case ASC:
                certificateCriteriaQuery.orderBy(criteriaBuilder.asc(giftCertificateRoot.get(queryParameter.getSortParameter().getParameter())));
                break;
            case DESC:
                certificateCriteriaQuery.orderBy(criteriaBuilder.desc(giftCertificateRoot.get(queryParameter.getSortParameter().getParameter())));
                break;
        }
        return certificateCriteriaQuery;
    }
}
