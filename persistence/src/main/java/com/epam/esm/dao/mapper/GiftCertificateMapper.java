package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        new GiftCertificate();
        return GiftCertificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .duration(rs.getInt("duration"))
                .createDate(rs.getTimestamp("createDate").toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp("lastUpdateDate").toLocalDateTime())
                .build();
    }
}
