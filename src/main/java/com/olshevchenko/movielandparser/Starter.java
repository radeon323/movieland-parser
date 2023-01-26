package com.olshevchenko.movielandparser;

import com.olshevchenko.movielandparser.service.MovieService;
import com.olshevchenko.movielandparser.service.ReviewService;
import com.olshevchenko.movielandparser.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Oleksandr Shevchenko
 */
@Component
@RequiredArgsConstructor
public class Starter {

    private final MovieService movieService;
    private final UserService userService;
    private final ReviewService reviewService;

    @PostConstruct
    public void run() {
        movieService.saveMovie();
        userService.saveUser();
        reviewService.saveReview();
    }
}
