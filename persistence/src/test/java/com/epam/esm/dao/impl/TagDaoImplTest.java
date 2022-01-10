package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceTestConfiguration;
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

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
@Transactional
class TagDaoImplTest {

    @Autowired
    private TagDaoImpl tagDao;
    private QueryParameter queryParameter;

    @BeforeEach
    void setUp() {
        queryParameter = QueryParameter.builder()
                .page(1)
                .size(10)
                .firstValue(1)
                .sortingDirection(QueryParameter.SortingDirection.ASC)
                .build();
    }

    @Test
    void testAdd_AllFieldsArePopulated_SavesDataToDatabase() {
        // Given
        Tag tag = Tag.builder().name("result1").build();
        // When
        Tag result = tagDao.add(tag);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("result1", result.getName());
    }

    @Test
    void testFindById_IdExists_ReadsDataFromDatabase() {
        // Given
        // When
        Tag result = tagDao.findById(1L).get();
        // Then
        Assertions.assertEquals(new Tag(1L, "people"), result);
        Assertions.assertFalse(tagDao.findById(999999999L).isPresent());
        Assertions.assertTrue(tagDao.findById(1L).isPresent());
    }

    @Test
    void testFindById_IdDoesNotExist_False() {
        // Given
        // When
        Optional<Tag> result = tagDao.findById(999999999L);
        // Then
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testFindAll_DatabaseExists_ReadsDataFromDatabase() {
        // Given
        // When
        List<Tag> result = tagDao.findAll(queryParameter);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.size());
    }

    @Test
    void testFindByName_NameExists_ReadsDataFromDatabase() {
        // Given
        // When
        Optional<Tag> result = tagDao.findByName("people");
        // Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(new Tag(1L, "people"), result.get());
    }

    @Test
    void testFindByName_NameDoesNotExist_False() {
        // Given
        // When
        Optional<Tag> result = tagDao.findByName("nonexistent");
        // Then
        Assertions.assertTrue(result.isEmpty());
    }
}
