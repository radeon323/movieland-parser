package com.olshevchenko.movielandparser.repository.mapper;

import com.olshevchenko.movielandparser.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Oleksandr Shevchenko
 */
public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String nameUkr = resultSet.getString("name_ukr");
        String nameNative = resultSet.getString("name_native");
        int year = resultSet.getInt("year");
        String description = resultSet.getString("description");
        double rating = resultSet.getDouble("rating");
        double price = resultSet.getDouble("price");
        String picturePath = resultSet.getString("picture_path");

        return Movie.builder()
                .id(id)
                .nameUkr(nameUkr)
                .nameNative(nameNative)
                .year(year)
                .description(description)
                .rating(rating)
                .price(price)
                .picturePath(picturePath)
                .build();
    }
}