package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.mapper.UserMapper;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String FIND_USER =
            "SELECT id, first_name, last_name, money\n" +
                    "FROM user\n" +
                    "WHERE id = ?";
    private static final String FIND_ALL_USER =
            "SELECT id, first_name, last_name, money\n" +
                    "FROM user";

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.query(FIND_USER, userMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USER, userMapper);
    }
}
