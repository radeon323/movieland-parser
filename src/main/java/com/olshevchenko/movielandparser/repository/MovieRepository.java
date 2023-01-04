package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
