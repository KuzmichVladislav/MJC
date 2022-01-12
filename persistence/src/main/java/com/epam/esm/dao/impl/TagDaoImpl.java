package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The Class TagDaoImpl is the implementation of the {@link TagDao} interface.
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final String GET_MOST_USED = "SELECT id, name\n" +
            "FROM (\n" +
            "         SELECT id, name, sum(cost) AS amount, (COUNT(id) * number_of_certificates) AS number_of_orders\n" +
            "         FROM (\n" +
            "                  SELECT t.id,\n" +
            "                         t.name,\n" +
            "                         (oc.gift_certificate_cost * oc.number_of_certificates) AS 'cost',\n" +
            "                         oc.number_of_certificates\n" +
            "                  FROM tag t\n" +
            "                           LEFT JOIN gift_certificate_has_tag gcti ON t.id = gcti.tag_id\n" +
            "                           LEFT JOIN gift_certificate gc ON gc.id = gcti.gift_certificate_id\n" +
            "                           LEFT JOIN order_certificates oc ON gc.id = oc.gift_certificate_id\n" +
            "                           LEFT JOIN orders o ON oc.order_id = o.id\n" +
            "                           LEFT JOIN user u ON o.user_id = u.id\n" +
            "                  WHERE u.id = ?1) AS inner_table\n" +
            "         GROUP BY id\n" +
            "         ORDER BY number_of_orders DESC, amount DESC\n" +
            "         LIMIT 1) AS outer_table";
    private static final String FIND_ALL_TAGS = "select t from Tag t order by t.name ";
    private static final String FIND_TAG_BY_NAME = "select t from Tag t where t.name = ?1";
    private static final String IS_TAG_PART_OF_GIFT_CERTIFICATE = "select gc from GiftCertificate gc left join gc.tags t WHERE t.id = ?1";
    private static final String TOTAL_NUMBER_OF_ITEMS = "select count(t) from Tag t";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag add(Tag tag) {
        this.entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll(QueryParameter queryParameter) {
        return entityManager.createQuery
                (FIND_ALL_TAGS + queryParameter.getSortingDirection(),
                        Tag.class)
                .setFirstResult(queryParameter.getFirstValue())
                .setMaxResults(queryParameter.getSize())
                .getResultList();
    }

    @Override
    public long getTotalNumberOfItems() {
        return (Long) entityManager.createQuery(TOTAL_NUMBER_OF_ITEMS).getSingleResult();
    }

    @Override
    public void remove(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(FIND_TAG_BY_NAME, Tag.class)
                .setParameter(1, name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<Tag> findMostUsedTag(long id) {
        return entityManager.createNativeQuery(GET_MOST_USED, Tag.class)
                .setParameter(1, id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public boolean isPartOfGiftCertificate(long id) {
        return entityManager.createQuery(IS_TAG_PART_OF_GIFT_CERTIFICATE, GiftCertificate.class)
                .setParameter(1, id)
                .getResultStream()
                .findFirst().isPresent();
    }
}
