package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        new Tag();
        return Tag.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
