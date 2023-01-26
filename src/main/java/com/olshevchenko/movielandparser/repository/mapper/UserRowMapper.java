package com.olshevchenko.movielandparser.repository.mapper;

import com.olshevchenko.movielandparser.entity.Role;
import com.olshevchenko.movielandparser.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Oleksandr Shevchenko
 */
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String nickName = resultSet.getString("nick_name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        Role role = Role.valueOf(resultSet.getString("role"));

        return User.builder()
                .id(id)
                .nickName(nickName)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}