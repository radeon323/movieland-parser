package com.olshevchenko.movielandparser.repository.mapper;

import com.olshevchenko.movielandparser.entity.Country;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Oleksandr Shevchenko
 */
public class CountryResultSetExtractor implements ResultSetExtractor<Optional<Country>> {

    @Override
    public Optional<Country> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.ofNullable(new CountryRowMapper().mapRow(resultSet, 0));
    }

}
