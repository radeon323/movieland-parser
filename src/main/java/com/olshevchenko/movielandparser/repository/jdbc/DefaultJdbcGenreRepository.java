package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Genre;
import com.olshevchenko.movielandparser.repository.JdbcGenreRepository;
import com.olshevchenko.movielandparser.repository.mapper.GenreResultSetExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
@RequiredArgsConstructor
public class DefaultJdbcGenreRepository implements JdbcGenreRepository {

    private static final String SAVE = "INSERT INTO genres (name) VALUES (:name);";
    private static final String SAVE_IDS = "INSERT INTO movie_genre (movie_id, genre_id) VALUES (:movie_id, :genre_id);";
    private static final String FIND_BY_NAME = "SELECT id, name FROM genres WHERE name = ?";
    private static final GenreResultSetExtractor GENRE_RESULT_SET_EXTRACTOR = new GenreResultSetExtractor();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", genre.getName());
        namedParameterJdbcTemplate.update(SAVE, parameters, keyHolder, new String[] { "id" });
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void saveIds(Long movieId, Long genreId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("movie_id", movieId);
        parameters.put("genre_id", genreId);
        namedParameterJdbcTemplate.update(SAVE_IDS, parameters);
    }

    @Override
    public Optional<Genre> findByName(String genreName) {
        return jdbcTemplate.query(FIND_BY_NAME, GENRE_RESULT_SET_EXTRACTOR, genreName);
    }
}
