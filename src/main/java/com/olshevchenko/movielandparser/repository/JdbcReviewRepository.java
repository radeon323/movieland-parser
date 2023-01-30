package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Review;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcReviewRepository {
    void save(Review review);
    void deleteAll();
    void setAlterSequence();
}
