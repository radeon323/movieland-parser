package com.olshevchenko.movielandparser.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Oleksandr Shevchenko
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "reader")
public class ParseConfig {

    private String moviesUrl;
    private String postersUrl;
    private String usersUrl;
    private String reviewsUrl;
}
