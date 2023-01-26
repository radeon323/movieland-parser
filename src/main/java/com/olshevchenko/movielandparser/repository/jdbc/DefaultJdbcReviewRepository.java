package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Review;
import com.olshevchenko.movielandparser.repository.JdbcReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
@RequiredArgsConstructor
public class DefaultJdbcReviewRepository implements JdbcReviewRepository {

    private static final String DELETE_ALL = "DELETE FROM reviews;";
    private static final String SAVE = "INSERT INTO reviews (movie_id, user_id, content) VALUES (:movie_id, :user_id, :content);";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Review review) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("movie_id", review.getMovieId());
        parameters.put("user_id", review.getUserId());
        parameters.put("content", review.getContent());
        namedParameterJdbcTemplate.update(SAVE, parameters);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }
}
