package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GiftCertificateCriteriaQueryCreator for creating a query based on parameters obtained from request parameters.
 */
@Component
public class GiftCertificateCriteriaQueryCreator {

    private static final String ID = "id";
    private static final String TAGS = "tags";
    private static final String IS_REMOVED = "isRemoved";
    private static final String NAME = "name";
    private static final String LIKE = "%%%s%%";
    private static final String DESCRIPTION = "description";

    /**
     * Gets criteria query for gift certificate.
     *
     * @param queryParameter the query parameter
     * @param entityManager  the entity manager
     * @return the criteria query
     */
    public CriteriaQuery<GiftCertificate> getGiftCertificateCriteriaQuery
    (GiftCertificateQueryParameter queryParameter, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> certificateCriteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = certificateCriteriaQuery.from(GiftCertificate.class);
        Join<Object, Object> tagRoot = giftCertificateRoot.join(TAGS);
        Predicate[] predicateArray = getPredicates(queryParameter, criteriaBuilder, giftCertificateRoot, tagRoot);
        certificateCriteriaQuery.select(giftCertificateRoot).where(predicateArray);
        addSortingDirection(queryParameter, criteriaBuilder, certificateCriteriaQuery, giftCertificateRoot);
        if (queryParameter.getTagNames().isPresent()) {
            certificateCriteriaQuery.groupBy(giftCertificateRoot.get(ID));
            certificateCriteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(giftCertificateRoot.get(ID)),
                    queryParameter.getTagNames().get().size()));
        }
        return certificateCriteriaQuery;
    }

    private Predicate[] getPredicates(GiftCertificateQueryParameter queryParameter, CriteriaBuilder criteriaBuilder,
                                      Root<GiftCertificate> giftCertificateRoot, Join<Object, Object> tagRoot) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isFalse(giftCertificateRoot.get(IS_REMOVED)));
        if (queryParameter.getName().isPresent()) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(NAME),
                    String.format(LIKE, queryParameter.getName().get())));
        }
        if (queryParameter.getDescription().isPresent()) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(DESCRIPTION),
                    String.format(LIKE, queryParameter.getDescription().get())));
        }
        if (queryParameter.getTagNames().isPresent()) {
            Expression<String> expression = tagRoot.get(NAME);
            Predicate parentPredicate = expression.in(queryParameter.getTagNames().get());
            predicates.add(parentPredicate);
        }
        Predicate[] predicateArray = new Predicate[predicates.size()];
        predicateArray = predicates.toArray(predicateArray);
        return predicateArray;
    }

    private void addSortingDirection(GiftCertificateQueryParameter queryParameter,
                                     CriteriaBuilder criteriaBuilder,
                                     CriteriaQuery<GiftCertificate> certificateCriteriaQuery,
                                     Root<GiftCertificate> giftCertificateRoot) {
        if (queryParameter.getSortingDirection() == GiftCertificateQueryParameter.SortingDirection.ASC) {
            certificateCriteriaQuery.orderBy(criteriaBuilder
                    .asc(giftCertificateRoot.get(queryParameter.getSortParameter().getParameter())));
        } else if (queryParameter.getSortingDirection() == GiftCertificateQueryParameter.SortingDirection.DESC) {
            certificateCriteriaQuery.orderBy(criteriaBuilder
                    .desc(giftCertificateRoot.get(queryParameter.getSortParameter().getParameter())));
        }
    }
}
