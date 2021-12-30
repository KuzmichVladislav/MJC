//package com.epam.esm.dao.impl;
//
//import com.epam.esm.dao.mapper.TagMapper;
//import com.epam.esm.entity.Tag;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import java.com.epam.esm.util.List;
//import java.com.epam.esm.util.Optional;
//
//class TagDaoImplTest {
//
//    private EmbeddedDatabase embeddedDatabase;
//    private TagDaoImpl tagDaoImpl;
//
//    @BeforeEach
//    public void setUp() {
//        embeddedDatabase = new EmbeddedDatabaseBuilder()
//                .addDefaultScripts()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
//        TagMapper tagMapper = new TagMapper();
//        tagDaoImpl = new TagDaoImpl(jdbcTemplate, tagMapper);
//    }
//
//    @Test
//    void testAdd_AllFieldsArePopulated_SavesDataToDatabase() {
//        // Given
//        Tag tag = Tag.builder().name("result").build();
//        // When
//        Tag result = tagDaoImpl.add(tag);
//        // Then
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals("result", result.getName());
//    }
//
//    @Test
//    void testFindById_IdExists_ReadsDataFromDatabase() {
//        // Given
//        // When
//        Tag result = tagDaoImpl.findById(1L).get();
//        // Then
//        Assertions.assertEquals(new Tag(1L, "name1"), result);
//        Assertions.assertFalse(tagDaoImpl.findById(999999999L).isPresent());
//        Assertions.assertTrue(tagDaoImpl.findById(1L).isPresent());
//    }
//
//    @Test
//    void testFindById_IdDoesNotExist_False() {
//        // Given
//        // When
//        Optional<Tag> result = tagDaoImpl.findById(999999999L);
//        // Then
//        Assertions.assertFalse(result.isPresent());
//    }
//
//    @Test
//    void testFindAll_DatabaseExists_ReadsDataFromDatabase() {
//        // Given
//        // When
//        List<Tag> result = tagDaoImpl.findAll();
//        // Then
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(20, result.size());
//    }
//
//    @Test
//    void testRemoveById_IdExists_RemovesDataFromDatabase() {
//        // Given
//        // When
//        // Then
//        Assertions.assertTrue(tagDaoImpl.removeById(1L));
//    }
//
//    @Test
//    void testRemoveById_IdDoesNotExist_False() {
//        // Given
//        // When
//        // Then
//        Assertions.assertFalse(tagDaoImpl.removeById(-1L));
//    }
//
//    @Test
//    void testFindByName_NameExists_ReadsDataFromDatabase() {
//        // Given
//        // When
//        Optional<Tag> result = tagDaoImpl.findByName("name1");
//        // Then
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(new Tag(1L, "name1"), result.get());
//    }
//
//    @Test
//    void testFindByName_NameDoesNotExist_False() {
//        // Given
//        // When
//        Optional<Tag> result = tagDaoImpl.findByName("nonexistent");
//        // Then
//        Assertions.assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testFindByCertificateId_IdExists_ReadsDataFromDatabase() {
//        // Given
//        // When
//        List<Tag> result = tagDaoImpl.findByCertificateId(1L);
//        // Then
//        Assertions.assertFalse(result.isEmpty());
//        Assertions.assertEquals(4, result.size());
//    }
//
//    @Test
//    void testFindByCertificateId_IdDoesNotExist_False() {
//        // Given
//        // When
//        List<Tag> result = tagDaoImpl.findByCertificateId(999999L);
//        // Then
//        Assertions.assertTrue(result.isEmpty());
//    }
//
//    @AfterEach
//    public void tearDown() {
//        embeddedDatabase.shutdown();
//        tagDaoImpl = null;
//    }
//}
