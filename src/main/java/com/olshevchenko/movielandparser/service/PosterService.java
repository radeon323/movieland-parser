package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.Poster;
import com.olshevchenko.movielandparser.repository.JdbcPosterRepository;
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
public class PosterService {

    private final JdbcPosterRepository posterRepository;
    private final ParseConfig config;
    private final UrlFileReader urlFileReader;

    @PostConstruct
    public void savePoster() {
        List<Poster> posters = parsePoster();
        for (Poster poster : posters) {
            posterRepository.save(poster);
        }
    }

    List<Poster> parsePoster() {
        String url = config.getPostersUrl();
        List<String> rows = urlFileReader.read(url);
        List<Poster> posters = new ArrayList<>();

        for (String row : rows) {
            Poster poster = Poster.builder().build();
            poster.setMovieName(row.split(" https:")[0]);
            poster.setPicturePath("https:" + row.split(" https:")[1]);
            posters.add(poster);
        }
        return posters;
    }
}
