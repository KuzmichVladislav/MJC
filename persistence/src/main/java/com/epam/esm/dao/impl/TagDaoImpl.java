package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The Class TagDaoImpl is the implementation of the {@link TagDao} interface.
 */
@Repository
public class TagDaoImpl implements TagDao {

//    private static final String ADD_TAG =
//            "INSERT INTO tag (name)\n" +
//                    "VALUES (?)";
//    private static final String FIND_TAG =
//            "SELECT id, name\n" +
//                    "FROM tag\n" +
//                    "WHERE id = ?";
//    private static final String FIND_ALL_TAG =
//            "SELECT id, name\n" +
//                    "FROM tag";
//    private static final String REMOVE_TAG =
//            "DELETE\n" +
//                    "FROM tag\n" +
//                    "WHERE id = ?";
//    private static final String FIND_TAG_BY_NAME =
//            "SELECT id, name\n" +
//                    "FROM tag\n" +
//                    "WHERE name = ?";
//    private static final String FIND_ALL_TAG_BY_CERTIFICATE_ID = "SELECT id, name\n" +
//            "FROM tag\n" +
//            "   LEFT JOIN gift_certificate_tag_include gcti on tag.id = gcti.tag_id\n" +
//            "WHERE gcti.gift_certificate_id = ?";
//
//    private final JdbcTemplate jdbcTemplate;
//    private final TagMapper tagMapper;
//
//    @Autowired
//    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.tagMapper = tagMapper;
//    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Tag add(Tag tag) {
        this.entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery("select t from Tag t", Tag.class)
                .getResultList();
    }

    @Override
    public boolean removeById(long id) {
//        return jdbcTemplate.update(REMOVE_TAG, id) > 0;
        return false;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery("select t from Tag t where t.name = ?1 ", Tag.class)
                .setParameter(1, name).getResultList()
                .stream().findFirst();
    }

    @Override
    public List<Tag> findByCertificateId(long giftCertificateId) {
//        return jdbcTemplate.query(FIND_ALL_TAG_BY_CERTIFICATE_ID,
//                tagMapper, giftCertificateId);
        return null;
    }
}
