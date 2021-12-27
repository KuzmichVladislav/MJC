package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.ApplicationPage;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The Class TagDaoImpl is the implementation of the {@link TagDao} interface.
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL_TAGS = "select t from Tag t order by t.id";
    private static final String FIND_TAG_BY_NAME = "select t from Tag t where t.name = ?1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Tag add(Tag tag) {
        this.entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public ApplicationPage<Tag> findAll(int page, int size) {
        List<Tag> resultList = entityManager.createQuery(FIND_ALL_TAGS, Tag.class)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
        ApplicationPage<Tag> tagPage = new ApplicationPage<>();
        tagPage.setPage(page);
        tagPage.setSize(size);
        tagPage.setPageList(resultList);
        long ceil = (Long) entityManager.createQuery("select count(t) from Tag t").getSingleResult();
        int totalPage = (int) Math.ceil(ceil / (double) size);
        tagPage.setTotalPage(totalPage);
        return tagPage;
    }

    @Override
    @Transactional
    public boolean remove(Tag tag) {
        if (entityManager.contains(tag)) {
            entityManager.remove(tag);
        } else {
            entityManager.remove(entityManager.merge(tag));
        }
        return tag != null;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(FIND_TAG_BY_NAME, Tag.class)
                .setParameter(1, name).getResultList()
                .stream().findFirst();
    }

    /*
 TODO: 12/27/2021
    public Optional<Tag> findMostUsedTag(String name) {
        return entityManager.createQuery("select t from Tag t where t.name = ?1", Tag.class)
                .setParameter(1, name).getResultList()
                .stream().findFirst();
    }
*/
}
