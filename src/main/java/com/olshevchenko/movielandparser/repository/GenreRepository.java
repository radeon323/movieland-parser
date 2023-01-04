package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
