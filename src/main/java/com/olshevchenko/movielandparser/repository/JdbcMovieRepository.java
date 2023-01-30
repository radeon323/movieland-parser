package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Movie;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcMovieRepository {
    Long save(Movie movie);
    Movie findByNameUkr(String nameUkr);
    List<Movie> findAll();
    void deleteAll();
    void setAlterSequence();
}
