package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class GiftCertificateMapper is the implementation of the {@link RowMapper} interface
 * to map the record in the database to the gift certificate entity and back.
 *
 * @author Vladislav Kuzmich
 */
@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return GiftCertificate.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .duration(resultSet.getInt("duration"))
                .createDate(resultSet.getTimestamp("create_date").toLocalDateTime())
                .lastUpdateDate(resultSet.getTimestamp("last_update_date").toLocalDateTime())
                .build();
    }
}
