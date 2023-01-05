package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Country;

import java.util.Optional;

/**
 * @author Oleksandr Shevchenko
 */
public interface JdbcCountryRepository {

    Long save(Country country);
    void saveIds(Long movieId, Long countryId);
    Optional<Country> findByName(String countryName);

}
