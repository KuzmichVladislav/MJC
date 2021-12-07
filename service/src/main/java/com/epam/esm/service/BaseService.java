package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface BaseService<T> {

    T add(T t);

    T findById(long id);

    List<T> findAll();

    boolean removeById(long id);
}
