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

@Repository
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(Tag tag) throws DuplicateKeyException {
        String SQL = "INSERT INTO tag (name) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, new String[]{"id"});
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Tag> readAll() {
        return jdbcTemplate.query("SELECT * FROM tag", new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag read(int id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?", new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public boolean delete(int id) {
        boolean isDelete = false;
        if (jdbcTemplate.update("DELETE FROM tag WHERE id=?", id) > 0) {
            isDelete = true;
        }
        return isDelete;
    }

    public void addTagToCertificate(int giftCertificate, int tag) {
        jdbcTemplate.update("INSERT INTO gift_certificate_tag_include VALUES(?, ?)", giftCertificate, tag);
    }

    public int findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT id FROM tag WHERE name=?", Integer.class, name);
    }

    public List<Tag> readAllTagsByCertificateId(int giftCertificateId) {
        return jdbcTemplate.query("SELECT id, name\n" +
                        "FROM tag\n" +
                        "   LEFT JOIN gift_certificate_tag_include gcti on tag.id = gcti.tag\n" +
                        "WHERE gcti.giftCertificate = ?",
                new BeanPropertyRowMapper<>(Tag.class), giftCertificateId);
    }
}