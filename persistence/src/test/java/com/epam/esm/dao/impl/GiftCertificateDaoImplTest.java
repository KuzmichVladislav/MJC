package com.epam.esm.dao.impl;

import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.RequestSqlParam;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.SqlParamConvertor;
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
    private GiftCertificateDaoImpl giftCertificateDaoImpl;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper();
        SqlParamConvertor sqlParamConvertor = new SqlParamConvertor();
        giftCertificateDaoImpl = new GiftCertificateDaoImpl(jdbcTemplate, giftCertificateMapper, sqlParamConvertor);
    }

    @Test
    void testFindById() {
        GiftCertificate result = giftCertificateDaoImpl.findById(1L).get();
        Assertions.assertEquals("name1", result.getName());
        Assertions.assertEquals("description1", result.getDescription());
        Assertions.assertEquals(new BigDecimal("1.00"), result.getPrice());
        Assertions.assertEquals(1, result.getDuration());
        Assertions.assertFalse(giftCertificateDaoImpl.findById(999999999L).isPresent());
        Assertions.assertTrue(giftCertificateDaoImpl.findById(1L).isPresent());
    }

    @Test
    void testFindAll() {
        Assertions.assertNotNull(giftCertificateDaoImpl.findAll());
        Assertions.assertEquals(20, giftCertificateDaoImpl.findAll().size());
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
                .tagList(Arrays.asList(new Tag(500L, "tag500"),
                        new Tag(500L, "tag500")))
                .build();
        GiftCertificate result = giftCertificateDaoImpl.add(giftCertificate);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTagList().size());
        Assertions.assertEquals("result", result.getName());
    }

/*
 TODO: 12/11/2021
    @Test
    public void testUpdate() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1)
                .description("result")
                .duration(1)
                .lastUpdateDate(LocalDateTime.now())
                .tagList(Arrays.asList(new Tag(500, "tag500"),
                        new Tag(500, "tag500")))
                .build();
        GiftCertificate result = giftCertificateDaoImpl.update(giftCertificate);
        GiftCertificate expResult = giftCertificateDaoImpl.findById(1).get();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTagList().size());
        Assertions.assertEquals(result,expResult);
    }
*/

    @Test
    void testRemoveById() {
        Assertions.assertTrue(giftCertificateDaoImpl.removeById(1L));
        Assertions.assertFalse(giftCertificateDaoImpl.removeById(-1L));
    }

    @Test
    void testFindAllCertificateByTagId() {
        Assertions.assertNotNull(giftCertificateDaoImpl.findAllCertificateByTagId(1L));
        Assertions.assertTrue(giftCertificateDaoImpl.findAllCertificateByTagId(999999999L).isEmpty());
        Assertions.assertEquals(4, giftCertificateDaoImpl.findAllCertificateByTagId(1L).size());
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
        List<GiftCertificate> result = giftCertificateDaoImpl.findAllSorted(requestParam);
        Assertions.assertEquals(3, giftCertificateDaoImpl.findAllSorted(requestParam).size());
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}