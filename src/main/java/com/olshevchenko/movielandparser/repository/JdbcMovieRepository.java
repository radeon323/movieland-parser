package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Movie;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcMovieRepository {

    Long save(Movie movie);
}
