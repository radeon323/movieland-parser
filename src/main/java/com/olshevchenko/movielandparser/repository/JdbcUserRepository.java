package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.User;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcUserRepository {
    void save(User user);
}
