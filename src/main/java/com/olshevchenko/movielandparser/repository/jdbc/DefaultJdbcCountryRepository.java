package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.Country;
import com.olshevchenko.movielandparser.repository.JdbcCountryRepository;
import com.olshevchenko.movielandparser.repository.mapper.CountryResultSetExtractor;
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
public class DefaultJdbcCountryRepository implements JdbcCountryRepository {

    private static final String SAVE = "INSERT INTO countries (name) VALUES (:name);";
    private static final String SAVE_IDS = "INSERT INTO movie_country (movie_id, country_id) VALUES (:movie_id, :country_id);";
    private static final String FIND_BY_NAME = "SELECT id, name FROM countries WHERE name = ?";
    private static final String DELETE_ALL = "DELETE FROM countries;";
    private static final String ALTER_SEQUENCE_1 = "ALTER SEQUENCE countries_id_seq RESTART WITH 1 INCREMENT BY 1";
    private static final String ALTER_SEQUENCE_50 = "ALTER SEQUENCE countries_id_seq START WITH 1 INCREMENT BY 50";
    private static final CountryResultSetExtractor COUNTRY_RESULT_SET_EXTRACTOR = new CountryResultSetExtractor();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Country country) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", country.getName());
        namedParameterJdbcTemplate.update(SAVE, parameters, keyHolder, new String[] { "id" });
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void saveIds(Long movieId, Long countryId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("movie_id", movieId);
        parameters.put("country_id", countryId);
        namedParameterJdbcTemplate.update(SAVE_IDS, parameters);
    }

    @Override
    public Optional<Country> findByName(String countryName) {
        return jdbcTemplate.query(FIND_BY_NAME, COUNTRY_RESULT_SET_EXTRACTOR, countryName);
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
