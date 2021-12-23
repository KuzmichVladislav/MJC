package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Class GiftCertificateDaoImpl is the implementation of the {@link GiftCertificateDao} interface.
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String FIND_CERTIFICATES_BY_PARAMETERS =
            "SELECT DISTINCT gift_certificate.id,\n" +
                    "                gift_certificate.name,\n" +
                    "                gift_certificate.description,\n" +
                    "                gift_certificate.price,\n" +
                    "                gift_certificate.duration,\n" +
                    "                gift_certificate.create_date,\n" +
                    "                gift_certificate.last_update_date\n" +
                    "FROM gift_certificate\n" +
                    "         LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.gift_certificate_id\n" +
                    "         LEFT JOIN tag t on t.id = gcti.tag_id\n";
    private static final String ADD_GIFT_CERTIFICATE =
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_GIFT_CERTIFICATE =
            "SELECT id, name, description, price, duration, create_date, last_update_date\n" +
                    "FROM gift_certificate\n" +
                    "WHERE id = ?";
    private static final String FIND_ALL_GIFT_CERTIFICATE =
            "SELECT id, name, description, price, duration, create_date, last_update_date\n" +
                    "FROM gift_certificate";
    private static final String UPDATE_GIFT_CERTIFICATE =
            "UPDATE gift_certificate\n" +
                    "SET name            = IFNULL(?, name),\n" +
                    "    description     = IFNULL(?, description),\n" +
                    "    price           = IFNULL(?, price),\n" +
                    "    duration        = IFNULL(?, duration),\n" +
                    "    last_update_date=?\n" +
                    "WHERE id = ?";
    private static final String REMOVE_GIFT_CERTIFICATE =
            "DELETE\n" +
                    "FROM gift_certificate\n" +
                    "WHERE id = ?";
    private static final String FIND_ALL_GIFT_CERTIFICATE_BY_TAG =
            "SELECT id, name, description, price, duration, create_date, last_update_date, gift_certificate_id, tag_id\n" +
                    "FROM gift_certificate\n" +
                    "LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.gift_certificate_id\n" +
                    "WHERE gcti.tag_id = ?";
    private static final String ADD_TAG_TO_CERTIFICATE =
            "INSERT INTO gift_certificate_tag_include\n" +
                    "VALUES (?, ?)";
    private static final String REMOVE_TAG_BY_CERTIFICATE_ID =
            "DELETE\n" +
                    "FROM gift_certificate_tag_include\n" +
                    "WHERE gift_certificate_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateQueryCreator giftCertificateQueryCreator;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  GiftCertificateMapper giftCertificateMapper,
                                  GiftCertificateQueryCreator giftCertificateQueryCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateQueryCreator = giftCertificateQueryCreator;
    }


    // TODO: 12/24/2021  
    @Override
    @Transactional
    public GiftCertificate add(GiftCertificate giftCertificate) {
            entityManager.persist(giftCertificate);
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
    public void removeFromTableGiftCertificateTagInclude(long giftCertificateId) {
        jdbcTemplate.update(REMOVE_TAG_BY_CERTIFICATE_ID, giftCertificateId);
    }

    @Override
    public List<GiftCertificate> findByParameters(GiftCertificateQueryParameter requestParameter) {
        String sqlQuery = FIND_CERTIFICATES_BY_PARAMETERS +
                giftCertificateQueryCreator.mapRequestParameters(requestParameter);
        return jdbcTemplate.query(sqlQuery, giftCertificateMapper);
    }
}
