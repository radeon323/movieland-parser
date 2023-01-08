package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.User;
import com.olshevchenko.movielandparser.repository.JdbcUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
@RequiredArgsConstructor
public class DefaultJdbcUserRepository implements JdbcUserRepository {

    private static final String SAVE = "INSERT INTO users (first_name, last_name, email, password) VALUES (:first_name, :last_name, :email, :password);";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", user.getFirstName());
        parameters.put("last_name", user.getLastName());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        namedParameterJdbcTemplate.update(SAVE, parameters);
    }
}
