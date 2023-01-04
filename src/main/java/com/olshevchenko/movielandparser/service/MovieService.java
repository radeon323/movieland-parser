package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.Country;
import com.olshevchenko.movielandparser.entity.Genre;
import com.olshevchenko.movielandparser.entity.Movie;
import com.olshevchenko.movielandparser.repository.CountryRepository;
import com.olshevchenko.movielandparser.repository.GenreRepository;
import com.olshevchenko.movielandparser.repository.MovieRepository;
import com.olshevchenko.movielandparser.utils.UrlFileReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Oleksandr Shevchenko
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final ParseConfig config;
    private final UrlFileReader urlFileReader;

    @PostConstruct
    public void addMovie() {
        List<Movie> movies = parseMovie();
        for (Movie movie : movies) {
            movieRepository.save(movie);
        }
    }

    List<Movie> parseMovie() {

        String url = config.getMoviesUrl();
        List<String> rows = urlFileReader.read(url);
        List<Movie> movies = new ArrayList<>();

        Set<String> countriesSet = new HashSet<>();
        Set<String> genresSet = new HashSet<>();

        Movie movie = new Movie();
        for (int i = 0; i < rows.size(); i++) {
            if (i % 7 == 0) {
                movie = new Movie();
                movie.setNameUkr(rows.get(i).split("/")[0]);
                movie.setNameEng(rows.get(i).split("/")[1]);
            } else if (i % 7 == 1) {
                movie.setYear(Integer.parseInt(rows.get(i)));
            } else if (i % 7 == 2) {
                String[] countriesString = rows.get(i).split(", ");
                countriesSet.addAll(Arrays.stream(countriesString).toList());
                Set<Country> countries = new HashSet<>();
                Country country = new Country();
                for (String countryString : countriesString) {
                    country.setName(countryString);
                    countries.add(country);
                }
                movie.setCountries(countries);
            }else if (i % 7 == 3) {
                String[] genresString = rows.get(i).split(", ");
                genresSet.addAll(Arrays.stream(genresString).toList());
                Set<Genre> genres = new HashSet<>();
                Genre genre = new Genre();
                for (String genreString : genresString) {
                    genre.setName(genreString);
                    genres.add(genre);
                }
                movie.setGenres(genres);
            } else if (i % 7 == 4) {
                movie.setDescription(rows.get(i));
            } else if (i % 7 == 5) {
                String row = rows.get(i).split(":")[1];
                movie.setRating(Double.parseDouble(row));
            } else if (i % 7 == 6) {
                String row = rows.get(i).split(":")[1];
                movie.setPrice(Double.parseDouble(row));
                movies.add(movie);
            }
        }

        addCountries(countriesSet);
        addGenres(genresSet);
        return movies;
    }

    void addCountries(Set<String> countriesSet) {
        for (String c : countriesSet) {
            Country country = new Country();
            country.setName(c);
            countryRepository.save(country);
        }
    }

    void addGenres(Set<String> genresSet) {
        for (String g : genresSet) {
            Genre genre = new Genre();
            genre.setName(g);
            genreRepository.save(genre);
        }
    }

}
