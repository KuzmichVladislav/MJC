package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    T add(T t);

    Optional<T> findById(long id);

    List<T> findAll();

    boolean removeById(long id);
}
