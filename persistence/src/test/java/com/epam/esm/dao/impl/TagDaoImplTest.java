package com.epam.esm.dao.impl;

import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class TagDaoImplTest {

    private EmbeddedDatabase embeddedDatabase;
    private TagDaoImpl tagDaoImpl;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        TagMapper tagMapper = new TagMapper();
        tagDaoImpl = new TagDaoImpl(jdbcTemplate, tagMapper);
    }

    @Test
    void testAdd() {
        Tag result = tagDaoImpl.add(Tag.builder().name("result").build());
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("result", result.getName());
    }

    @Test
    void testFindById() {
        Tag result = tagDaoImpl.findById(1L).get();
        Assertions.assertEquals(new Tag(1L, "name1"), result);
        Assertions.assertFalse(tagDaoImpl.findById(999999999L).isPresent());
        Assertions.assertTrue(tagDaoImpl.findById(1L).isPresent());
    }

    @Test
    void testFindAll() {
        Assertions.assertNotNull(tagDaoImpl.findAll());
        Assertions.assertEquals(20, tagDaoImpl.findAll().size());
    }

    @Test
    void testRemoveById() {
        Assertions.assertTrue(tagDaoImpl.removeById(1L));
        Assertions.assertFalse(tagDaoImpl.removeById(-1L));
    }

    @Test
    void testFindByName() {
        Tag result = tagDaoImpl.findByName("name1").get();
        Assertions.assertEquals(new Tag(1L, "name1"), result);
        Assertions.assertTrue(tagDaoImpl.findByName("nonexistent").isEmpty());
        Assertions.assertTrue(tagDaoImpl.findByName("name1").isPresent());
    }

    @Test
    void testFindByCertificateId() {
        Assertions.assertFalse(tagDaoImpl.findByCertificateId(1L).isEmpty());
        Assertions.assertTrue(tagDaoImpl.findByCertificateId(999999L).isEmpty());
        Assertions.assertEquals(4, tagDaoImpl.findByCertificateId(1L).size());
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}