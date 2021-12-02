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
    public int createTag(Tag tag) throws DuplicateKeyException {
//        jdbcTemplate.update("INSERT INTO tag (name) VALUES(?)", tag.getName());
//        return tag;

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
    public List<Tag> readAllTags() {
        return jdbcTemplate.query("SELECT * FROM tag", new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag readTag(int id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?", new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public boolean deleteTag(int id) {
        boolean isDelete = false;
        if (jdbcTemplate.update("DELETE FROM tag WHERE id=?", id) > 0) {
            isDelete = true;
        }
        return isDelete;
    }
    public int addTagToCertificate (int giftCertificate, int tag){
       return jdbcTemplate.update("INSERT INTO gift_certificate_tag_include VALUES(?, ?)", giftCertificate, tag);
    }

    public int findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT id FROM tag WHERE name=?", new Object[]{name}, Integer.class);

//        return jdbcTemplate.query("SELECT id FROM tag WHERE name=?", new BeanPropertyRowMapper<>(Tag.class), name)
//                .stream().findAny().orElse(null);
    }
}