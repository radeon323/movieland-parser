package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Movie;
import com.olshevchenko.movielandparser.repository.JdbcMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
@RequiredArgsConstructor
public class DefaultJdbcMovieRepository implements JdbcMovieRepository {

    private static final String SAVE = "INSERT INTO movies (name_ukr, name_eng, year, description, rating, price, picture_path) VALUES (:name_ukr, :name_eng, :year, :description, :rating, :price, :picture_path);";
    private static final String DELETE_ALL = "DELETE FROM movie_country; DELETE FROM movie_genre; DELETE FROM movies;";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name_ukr", movie.getNameUkr());
        parameters.addValue("name_eng", movie.getNameEng());
        parameters.addValue("year", movie.getYear());
        parameters.addValue("description", movie.getDescription());
        parameters.addValue("rating", movie.getRating());
        parameters.addValue("price", movie.getPrice());
        parameters.addValue("picture_path", movie.getPicturePath());
        namedParameterJdbcTemplate.update(SAVE, parameters, keyHolder, new String[] { "id" });
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }
}
