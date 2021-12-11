package com.epam.esm.dao.impl;

import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.List;

class TagDaoImplTest {

    private final EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
            .addDefaultScripts()
            .setType(EmbeddedDatabaseType.H2)
            .build();

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);

    private final TagMapper tagMapper = new TagMapper();

    private final TagDaoImpl tagDaoImpl = new TagDaoImpl(jdbcTemplate, tagMapper);

    /*
 FIXME: 12/11/2021
    @Before
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        tagDaoImpl = new TagDaoImpl(jdbcTemplate,tagMapper);
    }
*/

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
        Assertions.assertTrue(tagDaoImpl.findById(999999999L).isEmpty());
        Assertions.assertTrue(tagDaoImpl.findById(1L).isPresent());
    }

    @Test
    void testFindAll() {
        Assertions.assertNotNull(tagDaoImpl.findAll());
        Assertions.assertEquals(2, tagDaoImpl.findAll().size());
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
        List<Tag> result = tagDaoImpl.findByCertificateId(0L);
        Assertions.assertEquals(Arrays.<Tag>asList(new Tag(0L, "name")), result);
    }

    @After
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}