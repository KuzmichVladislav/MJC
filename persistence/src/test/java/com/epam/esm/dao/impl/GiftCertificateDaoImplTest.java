package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.RequestSqlParam;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.QueryParamCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class GiftCertificateDaoImplTest {

    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper();
        QueryParamCreator queryParamCreator = new QueryParamCreator();
        giftCertificateDao = new GiftCertificateDaoImpl(jdbcTemplate, giftCertificateMapper, queryParamCreator);
    }

    @Test
    void testFindById() {
        GiftCertificate result = giftCertificateDao.findById(1L).get();
        Assertions.assertEquals("name1", result.getName());
        Assertions.assertEquals("description1", result.getDescription());
        Assertions.assertEquals(new BigDecimal("1.00"), result.getPrice());
        Assertions.assertEquals(1, result.getDuration());
        Assertions.assertFalse(giftCertificateDao.findById(999999999L).isPresent());
        Assertions.assertTrue(giftCertificateDao.findById(1L).isPresent());
    }

    @Test
    void testFindAll() {
        Assertions.assertNotNull(giftCertificateDao.findAll());
        Assertions.assertEquals(20, giftCertificateDao.findAll().size());
    }

    @Test
    void testAdd() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("result")
                .description("result")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(new Tag(500L, "tag500"),
                        new Tag(500L, "tag500")))
                .build();
        GiftCertificate result = giftCertificateDao.add(giftCertificate);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTags().size());
        Assertions.assertEquals("result", result.getName());
    }

    @Test
    void testUpdate() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1)
                .description("result")
                .duration(1)
                .lastUpdateDate(LocalDateTime.now()) // FIXME: 12/13/2021
                .tags(Arrays.asList(new Tag(500, "tag500"),
                        new Tag(500, "tag500")))
                .build();
        GiftCertificate result = giftCertificateDao.update(giftCertificate);
        GiftCertificate expResult = giftCertificateDao.findById(1).get();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("result", result.getDescription());
        Assertions.assertEquals(1, result.getDuration());
        Assertions.assertEquals(2, result.getTags().size());
    }

    @Test
    void testRemoveById() {
        Assertions.assertTrue(giftCertificateDao.removeById(1L));
        Assertions.assertFalse(giftCertificateDao.removeById(-1L));
    }

    @Test
    void testFindAllCertificateByTagId() {
        Assertions.assertNotNull(giftCertificateDao.findAllCertificateByTagId(1L));
        Assertions.assertTrue(giftCertificateDao.findAllCertificateByTagId(999999999L).isEmpty());
        Assertions.assertEquals(4, giftCertificateDao.findAllCertificateByTagId(1L).size());
    }

    @Test
    void testFindAllSorted() {
        RequestSqlParam requestParam = RequestSqlParam.builder()
                .name(Optional.of("me1"))
                .tagName(Optional.of("name1"))
                .description(Optional.empty())
                .sort(Optional.empty())
                .orderBy(Optional.empty())
                .build();
        List<GiftCertificate> result = giftCertificateDao.findAllSorted(requestParam);
        Assertions.assertEquals(3, giftCertificateDao.findAllSorted(requestParam).size());
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}