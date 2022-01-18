package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.QueryParameter;
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
class OrderDaoImplTest {

    @Autowired
    private OrderDaoImpl orderDao;
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
    void testFindById_IdExists_ReadsDataFromDatabase() {
        // Given
        // When
        Optional<Order> result = orderDao.findById(1L);
        // Then
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void testFindById_IdDoesNotExist_False() {
        // Given
        // When
        Optional<Order> result = orderDao.findById(999999999L);
        // Then
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testFindAll_DatabaseExists_ReadsDataFromDatabase() {
        // Given
        // When
        List<Order> result = orderDao.findAll(queryParameter);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.size());
    }
}
