package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class TagDaoImpl implements TagDao {
    //    InitialContext initContext;
//    DataSource ds;
//    {
//        try {
//            initContext = new InitialContext();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
//    {
//        try {
//            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/dbconnect");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag createTag(Tag tag) {
        jdbcTemplate.update("INSERT INTO tag VALUES(?,?)", tag.getId(), tag.getName());
        return tag;
    }

    @Override
    public Tag readTag(int id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?", new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }
//    @Override
//    public Tag readTag(int id) {
//        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?", new Object[]{id}, new TagMapper())
//                .stream().findAny().orElse(null);
//    }

    @Override
    public boolean deleteTag(int id) {
        boolean isDelete = false;
        if (jdbcTemplate.update("DELETE FROM tag WHERE id=?", id) > 0){
            isDelete = true;
        }
        return isDelete;
    }
}