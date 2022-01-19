package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Class UserDaoImpl is the implementation of the {@link UserDao} interface.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL_USERS = "select u from User u order by u.username ";
    private static final String TOTAL_NUMBER_OF_ITEMS = "select count(u) from User u";
    private static final String FIND_USER_BY_LOGIN = "select u from User u where u.username = ?1";
    private static final String FIND_ROLES_BY_USER_ID = "SELECT roles FROM user_role WHERE user_id=";

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

    @Override
    public User findByUsername(String login) {
        User user = entityManager.createQuery(FIND_USER_BY_LOGIN, User.class)
                .setParameter(1, login)
                .getResultStream()
                .findFirst().get();
        Collection<Role> collect = (Collection) entityManager.createNativeQuery(FIND_ROLES_BY_USER_ID + user.getId())
                .getResultList().stream()
                .map(t -> Role.valueOf((String) t))
                .collect(Collectors.toSet());
        user.setRoles(new HashSet<>(collect));
        return user;
    }

    @Override
    public User add(User user) {
        this.entityManager.persist(user);
        return user;
    }
}
