package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    public static final String ADD_GIFT_CERTIFICATE_SQL = "INSERT INTO gift_certificate (name, description, price, duration) VALUES(?,?,?,?)";
    public static final String FIND_GIFT_CERTIFICATE_SQL = "SELECT id, name, description, price, duration, createDate, lastUpdateDate FROM gift_certificate WHERE id=?";
    public static final String FIND_ALL_GIFT_CERTIFICATE_SQL = "SELECT id, name, description, price, duration, createDate, lastUpdateDate FROM gift_certificate";
    public static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE gift_certificate SET name = IFNULL(?, name), description = IFNULL(?, description), price = IFNULL(?, price), duration = IFNULL(?, duration), lastUpdateDate=? WHERE id=?";
    public static final String REMOVE_GIFT_CERTIFICATE_SQL = "DELETE FROM gift_certificate WHERE id=?";
    public static final String FIND_ALL_GIFT_CERTIFICATE_BY_TAG_SQL = "SELECT id, name, description, price, duration, createDate, lastUpdateDate, giftCertificate, tag\n" +
            "FROM gift_certificate\n" +
            "LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.giftCertificate\n" +
            "WHERE gcti.tag = ?";
    public static final String ADD_TAG_TO_CERTIFICATE_SQL = "INSERT INTO gift_certificate_tag_include VALUES(?, ?)";
    public static final String REMOVE_TAG_BY_CERTIFICATE_ID_SQL = "DELETE FROM gift_certificate_tag_include WHERE giftCertificate=?";

    private final GiftCertificateMapper giftCertificateBeanPropertyRowMapper;

    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(GiftCertificateMapper giftCertificateBeanPropertyRowMapper, JdbcTemplate jdbcTemplate) {
        this.giftCertificateBeanPropertyRowMapper = giftCertificateBeanPropertyRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_GIFT_CERTIFICATE_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);
        giftCertificate.setId(keyHolder.getKey().longValue());
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATE_SQL,
                giftCertificateBeanPropertyRowMapper, id)
                .stream().findAny();
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> query = jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_SQL,
                giftCertificateBeanPropertyRowMapper);
        System.out.println(Arrays.asList(query));// TODO: 12/6/2021 all in console
        return query; // TODO: 12/6/2021 return only 1 GC in postman
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_SQL,
                giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public boolean removeById(long id) {
        return jdbcTemplate.update(REMOVE_GIFT_CERTIFICATE_SQL, id) > 0;
    }

    public List<GiftCertificate> findAllCertificateByTagId(long tagId) {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_BY_TAG_SQL,
                giftCertificateBeanPropertyRowMapper, tagId);
    }

    @Override
    public void addTagToCertificate(long giftCertificateId, long tagId) {
        jdbcTemplate.update(ADD_TAG_TO_CERTIFICATE_SQL, giftCertificateId, tagId);
    }

    @Override
    public void removeTagByCertificateId(long giftCertificateId) {
        jdbcTemplate.update(REMOVE_TAG_BY_CERTIFICATE_ID_SQL, giftCertificateId);
    }
}