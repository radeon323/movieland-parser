package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Poster;
import com.olshevchenko.movielandparser.repository.JdbcPosterRepository;
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
public class DefaultJdbcPosterRepository implements JdbcPosterRepository {

    private static final String SAVE = "INSERT INTO posters (movie_name, picture_path) VALUES (:movie_name, :picture_path);";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(Poster poster) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("movie_name", poster.getMovieName());
        parameters.put("picture_path", poster.getPicturePath());
        namedParameterJdbcTemplate.update(SAVE, parameters);
    }
}
