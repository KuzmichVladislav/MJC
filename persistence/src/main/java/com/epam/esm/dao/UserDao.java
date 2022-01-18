package com.epam.esm.dao;

import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface UserDao describes some query methods based on query object construction.
 */
public interface UserDao {

    /**
     * Find by user identifier.
     *
     * @param id the user identifier
     * @return the optional of user
     */
    Optional<User> findById(long id);

    /**
     * Find all list.
     *
     * @param queryParameter the query parameter
     * @return the list of users
     */
    List<User> findAll(QueryParameter queryParameter);

    /**
     * Gets total number of items.
     *
     * @return the total number of items
     */
    long getTotalNumberOfItems();
}
