package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Movie;
import com.olshevchenko.movielandparser.repository.JdbcMovieRepository;
import com.olshevchenko.movielandparser.repository.mapper.MovieRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
@RequiredArgsConstructor
public class DefaultJdbcMovieRepository implements JdbcMovieRepository {

    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final String SAVE = "INSERT INTO movies (name_ukr, name_native, year, description, rating, price, picture_path) VALUES (:name_ukr, :name_native, :year, :description, :rating, :price, :picture_path);";
    private static final String DELETE_ALL = "DELETE FROM movie_country; DELETE FROM movie_genre; DELETE FROM reviews; DELETE FROM movies;";
    private static final String ALTER_SEQUENCE_1 = "ALTER SEQUENCE movies_id_seq RESTART WITH 1 INCREMENT BY 1";
    private static final String ALTER_SEQUENCE_50 = "ALTER SEQUENCE movies_id_seq START WITH 1 INCREMENT BY 50";
    private static final String FIND_BY_NAME_UKR_SQL = "SELECT id, name_ukr, name_native, year, description, rating, price, picture_path FROM movies WHERE name_ukr = ?";
    private static final String FIND_ALL_SQL = "SELECT id, name_ukr, name_native, year, description, rating, price, picture_path FROM movies";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name_ukr", movie.getNameUkr());
        parameters.addValue("name_native", movie.getNameNative());
        parameters.addValue("year", movie.getYear());
        parameters.addValue("description", movie.getDescription());
        parameters.addValue("rating", movie.getRating());
        parameters.addValue("price", movie.getPrice());
        parameters.addValue("picture_path", movie.getPicturePath());
        namedParameterJdbcTemplate.update(SAVE, parameters, keyHolder, new String[] { "id" });
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Movie findByNameUkr(String nameUkr) {
        return jdbcTemplate.queryForObject(FIND_BY_NAME_UKR_SQL, MOVIE_ROW_MAPPER, nameUkr);
    }

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, MOVIE_ROW_MAPPER);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(ALTER_SEQUENCE_1);
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public void setAlterSequence() {
        jdbcTemplate.update(ALTER_SEQUENCE_50);
    }
}
