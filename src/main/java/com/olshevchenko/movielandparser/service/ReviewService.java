package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.Movie;
import com.olshevchenko.movielandparser.entity.Review;
import com.olshevchenko.movielandparser.entity.User;
import com.olshevchenko.movielandparser.repository.JdbcMovieRepository;
import com.olshevchenko.movielandparser.repository.JdbcReviewRepository;
import com.olshevchenko.movielandparser.repository.JdbcUserRepository;
import com.olshevchenko.movielandparser.utils.UrlFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Oleksandr Shevchenko
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JdbcMovieRepository movieRepository;
    private final JdbcReviewRepository reviewRepository;
    private final JdbcUserRepository userRepository;
    private final ParseConfig config;
    private final UrlFileReader urlFileReader;

    public void saveReview() {
        reviewRepository.deleteAll();
        List<Review> reviews = parseReview();
        for (Review review : reviews) {
            reviewRepository.save(review);
        }
    }

    List<Review> parseReview() {

        String url = config.getReviewsUrl();
        List<String> rows = urlFileReader.read(url);
        List<Review> reviews = new ArrayList<>();
        List<Movie> movies = movieRepository.findAll();
        List<User> users = userRepository.findAll();

        Review review = new Review();
        for (int i = 0; i < rows.size(); i++) {
            if (i % 3 == 0) {
                review = new Review();
                for (Movie movie : movies) {
                    String movieNameUkr = rows.get(i);
                    movieNameUkr = Utils.removeWaste(movieNameUkr);
                    if (Objects.equals(movie.getNameUkr(), movieNameUkr)) {
                        review.setMovieId(movie.getId());
                    }
                }
            } else if (i % 3 == 1) {
                for (User user : users) {
                    if (Objects.equals(user.getNickName(), rows.get(i))) {
                        review.setUserId(user.getId());
                    }
                }
            } else if (i % 3 == 2) {
                review.setContent(rows.get(i));
                reviews.add(review);
            }
        }
        return reviews;
    }
}
