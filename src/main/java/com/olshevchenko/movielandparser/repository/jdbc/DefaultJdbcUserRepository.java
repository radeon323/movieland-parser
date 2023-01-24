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

    private static final String SAVE = "INSERT INTO users (nick_name, email, password, role) VALUES (:nick_name, :email, :password, (:role::role));";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nick_name", user.getNickName());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("role", user.getRole().name());
        namedParameterJdbcTemplate.update(SAVE, parameters);
    }
}
