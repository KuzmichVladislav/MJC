package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryCreator;
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
        GiftCertificateQueryCreator giftCertificateQueryCreator = new GiftCertificateQueryCreator();
        giftCertificateDao = new GiftCertificateDaoImpl(jdbcTemplate, giftCertificateMapper, giftCertificateQueryCreator);
    }

    @Test
    void testFindById_IdExists_ReadsDataFromDatabase() {
        // Given
        // When
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(1L);
        // Then
        Assertions.assertTrue(giftCertificate.isPresent());
        Assertions.assertEquals("name1", giftCertificate.get().getName());
        Assertions.assertEquals("description1", giftCertificate.get().getDescription());
        Assertions.assertEquals(new BigDecimal("1.00"), giftCertificate.get().getPrice());
        Assertions.assertEquals(1, giftCertificate.get().getDuration());
        Assertions.assertFalse(giftCertificateDao.findById(999999999L).isPresent());
    }

    @Test
    void testFindById_IdDoesNotExist_False() {
        // Given
        // When
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(999999999L);
        // Then
        Assertions.assertFalse(giftCertificate.isPresent());
    }

    @Test
    void testFindAll_DatabaseExists_ReadsDataFromDatabase() {
        // Given
        // When
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        // Then
        Assertions.assertNotNull(giftCertificateList);
        Assertions.assertEquals(20, giftCertificateList.size());
    }

    @Test
    void testAdd_AllFieldsArePopulated_SavesDataToDatabase() {
        // Given
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
        // When
        GiftCertificate result = giftCertificateDao.add(giftCertificate);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTags().size());
        Assertions.assertEquals("result", result.getName());
    }

    @Test
    void testUpdate_AllFieldsArePopulated_UpdatesDataInDatabase() {
        // Given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1)
                .description("result")
                .duration(1)
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(new Tag(500, "tag500"),
                        new Tag(500, "tag500")))
                .build();
        // When
        GiftCertificate result = giftCertificateDao.update(giftCertificate);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("result", result.getDescription());
        Assertions.assertEquals(1, result.getDuration());
        Assertions.assertEquals(2, result.getTags().size());
    }

    @Test
    void testRemoveById_IdExists_RemovesDataFromDatabase() {
        // Given
        // When
        // Then
        Assertions.assertTrue(giftCertificateDao.removeById(1L));
    }

    @Test
    void testRemoveById_IdDoesNotExist_False() {
        // Given
        // When
        // Then
        Assertions.assertFalse(giftCertificateDao.removeById(-1L));
    }

    @Test
    void testFindAllCertificateByTagId_DatabaseExists_ReadsDataFromDatabase() {
        // Given
        // When
        List<GiftCertificate> allCertificateByTagId = giftCertificateDao.findAllCertificateByTagId(1L);
        // Then
        Assertions.assertNotNull(allCertificateByTagId);
        Assertions.assertEquals(4, allCertificateByTagId.size());
    }

    @Test
    void testFindAllCertificateByTagId_DatabaseExists_EmptyList() {
        // Given
        // When
        List<GiftCertificate> allCertificateByTagId = giftCertificateDao.findAllCertificateByTagId(999999999L);
        // Then
        Assertions.assertTrue(allCertificateByTagId.isEmpty());
    }


    @Test
    void testFindAllSorted_DatabaseExists_ReadsDataFromDatabase() {
        // Given
        GiftCertificateQueryParameter requestParam = GiftCertificateQueryParameter.builder()
                .name(Optional.of("me1"))
                .tagName(Optional.of("name1"))
                .description(Optional.empty())
                .sortType(Optional.empty())
                .sortOrder(Optional.empty())
                .build();
        // When
        List<GiftCertificate> GiftCertificateList = giftCertificateDao.findByParameters(requestParam);
        // Then
        Assertions.assertEquals(3, GiftCertificateList.size());
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
        giftCertificateDao = null;
    }
}
