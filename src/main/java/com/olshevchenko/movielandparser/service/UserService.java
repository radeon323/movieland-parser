package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.User;
import com.olshevchenko.movielandparser.repository.JdbcUserRepository;
import com.olshevchenko.movielandparser.utils.UrlFileReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final JdbcUserRepository userRepository;
    private final ParseConfig config;
    private final UrlFileReader urlFileReader;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void saveUser() {
        List<User> users = parseUser();
        for (User user : users) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }

    List<User> parseUser() {
        String url = config.getUsersUrl();
        List<String> rows = urlFileReader.read(url);
        List<User> users = new ArrayList<>();

        User user = new User();
        for (int i = 0; i < rows.size(); i++) {
            if (i % 3 == 0) {
                user = new User();
                user.setNickName(rows.get(i));
            } else if (i % 3 == 1) {
                user.setEmail(rows.get(i));
            } else if (i % 3 == 2) {
                user.setPassword(rows.get(i));
                users.add(user);
            }
        }
        return users;
    }
}
