package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Genre;

import java.util.Optional;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcGenreRepository {
    Long save(Genre genre);
    void saveIds(Long movieId, Long genreId);
    Optional<Genre> findByName(String genreName);
}
