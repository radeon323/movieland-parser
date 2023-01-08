package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.Review;
import com.olshevchenko.movielandparser.repository.JdbcReviewRepository;
import com.olshevchenko.movielandparser.utils.UrlFileReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JdbcReviewRepository reviewRepository;
    private final ParseConfig config;
    private final UrlFileReader urlFileReader;

    @PostConstruct
    public void saveReview() {
        List<Review> reviews = parseReview();
        for (Review review : reviews) {
            reviewRepository.save(review);
        }
    }

    List<Review> parseReview() {
        String url = config.getReviewsUrl();
        List<String> rows = urlFileReader.read(url);
        List<Review> reviews = new ArrayList<>();

        Review review = new Review();
        for (int i = 0; i < rows.size(); i++) {
            if (i % 3 == 0) {
                review = new Review();
                review.setMovieName(rows.get(i));
            } else if (i % 3 == 1) {
                review.setUserFirstName(rows.get(i).split(" ")[0]);
                review.setUserLastName(rows.get(i).split(" ")[1]);
            } else if (i % 3 == 2) {
                review.setContent(rows.get(i));
                reviews.add(review);
            }
        }
        return reviews;
    }
}
