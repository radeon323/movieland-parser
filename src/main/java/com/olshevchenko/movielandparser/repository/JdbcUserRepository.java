package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.User;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcUserRepository {
    void save(User user);
    User findByNickName(String nickName);
    List<User> findAll();
    void deleteAll();
}
