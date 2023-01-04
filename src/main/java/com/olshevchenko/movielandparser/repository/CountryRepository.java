package com.olshevchenko.movielandparser.repository;

import com.olshevchenko.movielandparser.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}