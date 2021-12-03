package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    public static final String ADD_GIFT_CERTIFICATE_SQL = "INSERT INTO gift_certificate (name, description, price, duration) VALUES(?,?,?,?)";
    public static final String FIND_GIFT_CERTIFICATE_SQL = "SELECT * FROM gift_certificate WHERE id=?";
    public static final String FIND_ALL_GIFT_CERTIFICATE_SQL = "SELECT id, name, description, price, duration, createDate, lastUpdateDate FROM gift_certificate";
    public static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, lastUpdateDate=? WHERE id=?";
    public static final String REMOVE_GIFT_CERTIFICATE_SQL = "DELETE FROM gift_certificate WHERE id=?";
    public static final String FIND_ALL_GIFT_CERTIFICATE_BY_TAG_SQL = "SELECT id, name, description, price, duration, createDate, lastUpdateDate, giftCertificate, tag\n" +
                    "FROM gift_certificate\n" +
                    "LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.giftCertificate\n" +
                    "WHERE gcti.tag = ?";
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_GIFT_CERTIFICATE_SQL, new String[]{"id"});
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);
        return findGiftCertificateById(keyHolder.getKey().intValue());
    }

    @Override
    public GiftCertificate findGiftCertificateById(int id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATE_SQL,
                new BeanPropertyRowMapper<>(GiftCertificate.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificates() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_SQL,
                new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_SQL,
                giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(), id);
        return giftCertificate;
    }

    @Override
    public boolean removeGiftCertificateById(int id) {
        boolean isDelete = false;
        if (jdbcTemplate.update(REMOVE_GIFT_CERTIFICATE_SQL, id) > 0) {
            isDelete = true;
        }
        return isDelete;
    }

    public List<GiftCertificate> readAllCertificateByTagId(int tagId) {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_BY_TAG_SQL,
                new BeanPropertyRowMapper<>(GiftCertificate.class), tagId);
    }

}
