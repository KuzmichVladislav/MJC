package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class TagMapper class is the implementation of the {@link RowMapper} interface
 * to map the record in the database to the tag entity and back.
 *
 * @author Vladislav Kuzmich
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return Tag.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
