package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The Class UserDaoImpl is the implementation of the {@link UserDao} interface.
 */
@Repository
public class UserDaoImpl implements UserDao {

    public static final String FIND_ALL_USERS = "select u from User u order by u.login ";
    private static final String TOTAL_NUMBER_OF_ITEMS = "select count(u) from User u";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(QueryParameter queryParameter) {
        return entityManager.createQuery
                (FIND_ALL_USERS + queryParameter.getSortingDirection(),
                        User.class)
                .setFirstResult(queryParameter.getFirstValue())
                .setMaxResults(queryParameter.getSize())
                .getResultList();
    }

    public long getTotalNumberOfItems() {
        return (Long) entityManager.createQuery(TOTAL_NUMBER_OF_ITEMS).getSingleResult();
    }
}
