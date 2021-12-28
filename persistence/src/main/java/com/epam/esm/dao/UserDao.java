package com.epam.esm.dao;

import com.epam.esm.entity.ApplicationPage;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    List<User> findAll(ApplicationPage page);

    long getTotalNumberOfItems();
}
