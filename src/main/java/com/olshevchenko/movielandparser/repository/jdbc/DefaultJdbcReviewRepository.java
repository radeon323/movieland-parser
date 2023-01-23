package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Review;
import com.olshevchenko.movielandparser.repository.JdbcReviewRepository;
import lombok.RequiredArgsConstructor;
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

    private static final String SAVE = "INSERT INTO reviews (movie_name, user_nick_name, content) VALUES (:movie_name, :user_nick_name, :content);";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(Review review) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("movie_name", review.getMovieName());
        parameters.put("user_nick_name", review.getUserNickName());
        parameters.put("content", review.getContent());
        namedParameterJdbcTemplate.update(SAVE, parameters);
    }
}
