package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao { // TODO: 12/24/2021 add extends

    Optional<User> findById(long id);

    List<User> findAll();
}
