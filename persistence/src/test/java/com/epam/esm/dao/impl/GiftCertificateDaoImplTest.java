package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
@Transactional
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;
    private QueryParameter queryParameter;

    @BeforeEach
    void setUp() {
        queryParameter = QueryParameter.builder()
                .name(Optional.ofNullable(null))
                .description(Optional.ofNullable(null))
                .tagNames(Optional.ofNullable(null))
                .sortParameter(QueryParameter.SortParameter.NAME)
                .page(1)
                .size(10)
                .firstValue(1)
                .sortingDirection(QueryParameter.SortingDirection.ASC)
                .build();
    }

    @Test
    void testFindById_IdExists_ReadsDataFromDatabase() {
        // Given
        // When
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(1L);
        // Then
        Assertions.assertTrue(giftCertificate.isPresent());
        Assertions.assertEquals("ornare.", giftCertificate.get().getName());
        Assertions.assertEquals("fames ac turpis egestas. Aliquam fringilla cursus purus. Nullam scelerisque neque sed sem egestas blandit. Nam", giftCertificate.get().getDescription());
        Assertions.assertEquals(new BigDecimal("221.70"), giftCertificate.get().getPrice());
        Assertions.assertEquals(285, giftCertificate.get().getDuration());
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
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll(queryParameter);
        // Then
        Assertions.assertNotNull(giftCertificateList);
        Assertions.assertEquals(10, giftCertificateList.size());
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
                .id(1L)
                .description("result")
                .duration(1)
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(new Tag(500, "tag500"),
                        new Tag(500, "tag500")))
                .build();
        // When
        giftCertificateDao.update(giftCertificate);
        GiftCertificate result = giftCertificateDao.findById(1L).get();
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
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .description("result")
                .duration(1)
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(new Tag(500, "tag500"),
                        new Tag(500, "tag500")))
                .build();
        // When
        // Then
        Assertions.assertTrue(giftCertificateDao.remove(giftCertificate));
    }

    @Test
    void testRemoveById_IdDoesNotExist_False() {
        // Given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(-1L)
                .description("result")
                .duration(1)
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(new Tag(500, "tag500"),
                        new Tag(500, "tag500")))
                .build();
        // When
        // Then
        Assertions.assertFalse(giftCertificateDao.remove(giftCertificate));
    }

    @Test
    void testFindAllSorted_DatabaseExists_ReadsDataFromDatabase() {
        // Given

        // When
        List<GiftCertificate> GiftCertificateList = giftCertificateDao.findAll(queryParameter);
        // Then
        Assertions.assertEquals(10, GiftCertificateList.size());
    }
}
