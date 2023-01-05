package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Poster;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcPosterRepository {
    void save(Poster poster);
}
