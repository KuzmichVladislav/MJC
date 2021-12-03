package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    public static final String ADD_TAG_SQL = "INSERT INTO tag (name) VALUES(?)";
    public static final String FIND_TAG_SQL = "SELECT * FROM tag WHERE id=?";
    public static final String FIND_ALL_TAG_SQL = "SELECT id, name FROM tag";
    public static final String REMOVE_TAG_SQL = "DELETE FROM tag WHERE id=?";
    public static final String ADD_TAG_TO_CERTIFICATE_SQL = "INSERT INTO gift_certificate_tag_include VALUES(?, ?)";
    public static final String READ_ALL_TAG_BY_CERTIFICATE_ID_SQL = "SELECT id, name\n" +
                    "FROM tag\n" +
                    "   LEFT JOIN gift_certificate_tag_include gcti on tag.id = gcti.tag\n" +
                    "WHERE gcti.giftCertificate = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag addTag(Tag tag) throws DuplicateKeyException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TAG_SQL, new String[]{"id"});
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        return findTagById(keyHolder.getKey().intValue());
    }

    @Override
    public Tag findTagById(int id) {
        return jdbcTemplate.query(FIND_TAG_SQL, new BeanPropertyRowMapper<>(Tag.class), id).stream()
                .findAny().orElse(null);// FIXME: 12/3/2021 what return?
    }

    @Override
    public List<Tag> findAllTags() {
        return jdbcTemplate.query(FIND_ALL_TAG_SQL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public boolean removeTagById(int id) {
        boolean isDelete = false;
        if (jdbcTemplate.update(REMOVE_TAG_SQL, id) > 0) {
            isDelete = true;
        }
        return isDelete;
    }

    public void addTagToCertificate(int giftCertificate, int tag) {
        jdbcTemplate.update(ADD_TAG_TO_CERTIFICATE_SQL, giftCertificate, tag);
    }

    public Optional<Tag> findTagByName(String name) {
        return jdbcTemplate.query("SELECT id FROM tag WHERE name=?",
                new BeanPropertyRowMapper<>(Tag.class), name).stream().findAny();
    }

    public List<Tag> readAllTagsByCertificateId(int giftCertificateId) {
        return jdbcTemplate.query(READ_ALL_TAG_BY_CERTIFICATE_ID_SQL,
                new BeanPropertyRowMapper<>(Tag.class), giftCertificateId);
    }
}