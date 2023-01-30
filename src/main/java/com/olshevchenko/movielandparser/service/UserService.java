package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.Role;
import com.olshevchenko.movielandparser.entity.User;
import com.olshevchenko.movielandparser.repository.JdbcUserRepository;
import com.olshevchenko.movielandparser.utils.UrlFileReader;
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

    public void saveUser() {
        userRepository.deleteAll();
        List<User> users = parseUser();
        for (User user : users) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        userRepository.setAlterSequence();
    }

    List<User> parseUser() {
        String url = config.getUsersUrl();
        List<String> rows = urlFileReader.read(url);
        List<User> users = new ArrayList<>();

        User user = User.builder().build();
        for (int i = 0; i < rows.size(); i++) {
            if (i % 3 == 0) {
                user = User.builder().build();
                user.setRole(Role.USER);
                String nickName = rows.get(i);
                nickName = Utils.removeWaste(nickName);
                user.setNickName(nickName);
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
