package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String ADD_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, createDate, lastUpdateDate) VALUES(?,?,?,?,?,?)";
    private static final String FIND_GIFT_CERTIFICATE = "SELECT id, name, description, price, duration, createDate, lastUpdateDate FROM gift_certificate WHERE id=?";
    private static final String FIND_ALL_GIFT_CERTIFICATE = "SELECT id, name, description, price, duration, createDate, lastUpdateDate FROM gift_certificate";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name = IFNULL(?, name), description = IFNULL(?, description), price = IFNULL(?, price), duration = IFNULL(?, duration), lastUpdateDate=? WHERE id=?";
    private static final String REMOVE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private static final String FIND_ALL_GIFT_CERTIFICATE_BY_TAG = "SELECT id, name, description, price, duration, createDate, lastUpdateDate, giftCertificate, tag\n" +
            "FROM gift_certificate\n" +
            "LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.giftCertificate\n" +
            "WHERE gcti.tag = ?";
    private static final String ADD_TAG_TO_CERTIFICATE = "INSERT INTO gift_certificate_tag_include VALUES(?, ?)";
    private static final String REMOVE_TAG_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag_include WHERE giftCertificate=?";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_GIFT_CERTIFICATE,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
            return preparedStatement;
        }, keyHolder);
        giftCertificate.setId(keyHolder.getKey().longValue());
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATE,
                giftCertificateMapper, id)
                .stream().findFirst();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE,
                giftCertificateMapper);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public boolean removeById(long id) {
        return jdbcTemplate.update(REMOVE_GIFT_CERTIFICATE, id) > 0;
    }

    public List<GiftCertificate> findAllCertificateByTagId(long tagId) {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_BY_TAG,
                giftCertificateMapper, tagId);
    }

    @Override
    public void addTagToCertificate(long giftCertificateId, long tagId) {
        jdbcTemplate.update(ADD_TAG_TO_CERTIFICATE, giftCertificateId, tagId);
    }

    @Override
    public void removeFromTableGiftCertificateIncludeTag(long giftCertificateId) {
        jdbcTemplate.update(REMOVE_TAG_BY_CERTIFICATE_ID, giftCertificateId);
    }
}