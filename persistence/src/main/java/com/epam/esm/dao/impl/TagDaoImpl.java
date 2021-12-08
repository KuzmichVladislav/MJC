package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String ADD_TAG = "INSERT INTO tag (name) VALUES(?)";
    private static final String FIND_TAG = "SELECT id, name FROM tag WHERE id=?";
    private static final String FIND_ALL_TAG = "SELECT id, name FROM tag";
    private static final String REMOVE_TAG = "DELETE FROM tag WHERE id=?";
    private static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private TagMapper tagBeanPropertyRowMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag add(Tag tag) throws DuplicateKeyException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TAG,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(FIND_TAG, tagBeanPropertyRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAG, tagBeanPropertyRowMapper);
    }

    @Override
    public boolean removeById(long id) {
        return jdbcTemplate.update(REMOVE_TAG, id) > 0;
    }

    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(FIND_TAG_BY_NAME,
                tagBeanPropertyRowMapper, name).stream().findAny();
    }
}