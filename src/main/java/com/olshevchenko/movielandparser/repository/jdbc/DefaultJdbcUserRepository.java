package com.olshevchenko.movielandparser.repository.jdbc;

import com.olshevchenko.movielandparser.entity.User;
import com.olshevchenko.movielandparser.repository.JdbcUserRepository;
import com.olshevchenko.movielandparser.repository.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
@Repository
@RequiredArgsConstructor
public class DefaultJdbcUserRepository implements JdbcUserRepository {

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String DELETE_ALL = "DELETE FROM users;";
    private static final String ALTER_SEQUENCE_1 = "ALTER SEQUENCE users_id_seq RESTART WITH 1 INCREMENT BY 1";
    private static final String ALTER_SEQUENCE_50 = "ALTER SEQUENCE users_id_seq START WITH 1 INCREMENT BY 50";
    private static final String SAVE = "INSERT INTO users (nick_name, email, password, role) VALUES (:nick_name, :email, :password, (:role::role));";
    private static final String FIND_BY_NICK_NAME_SQL = "SELECT id, nick_name, email, password, role FROM users WHERE nick_name = ?";
    private static final String FIND_ALL_SQL = "SELECT id, nick_name, email, password, role FROM users";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nick_name", user.getNickName());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("role", user.getRole().name());
        namedParameterJdbcTemplate.update(SAVE, parameters);
    }

    @Override
    public User findByNickName(String nickName) {
        return jdbcTemplate.queryForObject(FIND_BY_NICK_NAME_SQL, USER_ROW_MAPPER, nickName);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, USER_ROW_MAPPER);
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
