package com.olshevchenko.movielandparser.repository.mapper;

import com.olshevchenko.movielandparser.entity.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Oleksandr Shevchenko
 */
public class GenreResultSetExtractor implements ResultSetExtractor<Optional<Genre>> {

    @Override
    public Optional<Genre> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.ofNullable(new GenreRowMapper().mapRow(resultSet, 0));
    }

}
