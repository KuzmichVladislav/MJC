package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.ApplicationPage;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCertificates;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String FIND_ALL_ORDERS = "select o from Order o";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Order add(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    @Transactional
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public ApplicationPage<Order> findAll(int page, int size) {
        // TODO: 12/27/2021
//        return entityManager.createQuery(FIND_ALL_ORDERS, Order.class)
//                .getResultList();
        return null;
    }


    @Override
    @Transactional
    public boolean remove(Order order) {
        if (entityManager.contains(order)) {
            entityManager.remove(order);
        } else {
            entityManager.remove(entityManager.merge(order));
        }
        return order != null; // FIXME: 12/27/2021
    }

    @Override
    @Transactional
    public void addGiftCertificateToOrder(OrderCertificates orderCertificates) {
        entityManager.persist(orderCertificates);
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return entityManager.createQuery("select o from Order o where o.userId = ?1", Order.class)
                .setParameter(1, userId)
                .getResultList();
    }
}
