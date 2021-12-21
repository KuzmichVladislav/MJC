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

/**
 * The Class TagDaoImpl is the implementation of the {@link TagDao} interface.
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final String ADD_TAG =
            "INSERT INTO tag (name)\n" +
                    "VALUES (?)";
    private static final String FIND_TAG =
            "SELECT id, name\n" +
                    "FROM tag\n" +
                    "WHERE id = ?";
    private static final String FIND_ALL_TAG =
            "SELECT id, name\n" +
                    "FROM tag";
    private static final String REMOVE_TAG =
            "DELETE\n" +
                    "FROM tag\n" +
                    "WHERE id = ?";
    private static final String FIND_TAG_BY_NAME =
            "SELECT id, name\n" +
                    "FROM tag\n" +
                    "WHERE name = ?";
    private static final String FIND_ALL_TAG_BY_CERTIFICATE_ID = "SELECT id, name\n" +
            "FROM tag\n" +
            "   LEFT JOIN gift_certificate_tag_include gcti on tag.id = gcti.tag\n" +
            "WHERE gcti.gift_certificate = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
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
        return jdbcTemplate.query(FIND_TAG, tagMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAG, tagMapper);
    }

    @Override
    public boolean removeById(long id) {
        return jdbcTemplate.update(REMOVE_TAG, id) > 0;
    }

    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(FIND_TAG_BY_NAME,
                tagMapper, name).stream().findFirst();
    }

    @Override
    public List<Tag> findByCertificateId(long giftCertificateId) {
        return jdbcTemplate.query(FIND_ALL_TAG_BY_CERTIFICATE_ID,
                tagMapper, giftCertificateId);
    }
}
