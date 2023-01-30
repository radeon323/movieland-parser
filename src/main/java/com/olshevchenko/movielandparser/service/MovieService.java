package com.olshevchenko.movielandparser.service;

import com.olshevchenko.movielandparser.config.ParseConfig;
import com.olshevchenko.movielandparser.entity.Country;
import com.olshevchenko.movielandparser.entity.Genre;
import com.olshevchenko.movielandparser.entity.Movie;
import com.olshevchenko.movielandparser.entity.Poster;
import com.olshevchenko.movielandparser.repository.JdbcCountryRepository;
import com.olshevchenko.movielandparser.repository.JdbcGenreRepository;
import com.olshevchenko.movielandparser.repository.JdbcMovieRepository;
import com.olshevchenko.movielandparser.utils.UrlFileReader;
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

    private final JdbcMovieRepository movieRepository;
    private final JdbcCountryRepository countryRepository;
    private final JdbcGenreRepository genreRepository;
    private final ParseConfig config;
    private final UrlFileReader urlFileReader;


    public void saveMovie() {
        movieRepository.deleteAll();
        countryRepository.deleteAll();
        genreRepository.deleteAll();

        List<Movie> moviesWithoutPoster = parseMovie();
        List<Movie> movies = addPoster(moviesWithoutPoster);

        for (Movie movie : movies) {
            Long movieId = movieRepository.save(movie);

            Set<Country> countries = movie.getCountries();
            for (Country c : countries) {
                Optional<Country> country = countryRepository.findByName(c.getName());
                Long countryId;
                if (country.isEmpty()) {
                    countryId = countryRepository.save(c);
                } else {
                    countryId = country.get().getId();
                }
                countryRepository.saveIds(movieId, countryId);
            }

            Set<Genre> genres = movie.getGenres();
            for (Genre g : genres) {
                Optional<Genre> genre = genreRepository.findByName(g.getName());
                Long genreId;
                if (genre.isEmpty()) {
                    genreId = genreRepository.save(g);
                } else {
                    genreId = genre.get().getId();
                }
                genreRepository.saveIds(movieId, genreId);
            }

        }
        movieRepository.setAlterSequence();
        countryRepository.setAlterSequence();
        genreRepository.setAlterSequence();
    }

    List<Movie> parseMovie() {

        String url = config.getMoviesUrl();
        List<String> rows = urlFileReader.read(url);
        List<Movie> movies = new ArrayList<>();

        Movie movie = Movie.builder().build();
        for (int i = 0; i < rows.size(); i++) {
            if (i % 7 == 0) {
                movie = Movie.builder().build();
                String[] name = rows.get(i).split("/");
                String nameUkr = name[0];
                String nameEng = name[1];
                nameUkr = Utils.removeWaste(nameUkr);
                movie.setNameUkr(nameUkr);
                movie.setNameEng(nameEng);
            } else if (i % 7 == 1) {
                movie.setYear(Integer.parseInt(rows.get(i)));
            } else if (i % 7 == 2) {
                String[] countriesString = rows.get(i).split(", ");
                Set<Country> countries = new HashSet<>();
                for (String countryString : countriesString) {
                    Country country = Country.builder()
                            .name(countryString)
                            .build();
                    countries.add(country);
                }
                movie.setCountries(countries);
            }else if (i % 7 == 3) {
                String[] genresString = rows.get(i).split(", ");
                Set<Genre> genres = new HashSet<>();
                for (String genreString : genresString) {
                    Genre genre = Genre.builder()
                            .name(genreString)
                            .build();
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
        return movies;
    }

    List<Poster> parsePoster() {
        String url = config.getPostersUrl();
        List<String> rows = urlFileReader.read(url);
        List<Poster> posters = new ArrayList<>();

        for (String row : rows) {

            String before = row.split(" https:")[0];
            before = Utils.removeWaste(before);

            Poster poster = Poster.builder()
                    .movieName(before)
                    .picturePath("https:" + row.split(" https:")[1])
                    .build();
            posters.add(poster);
        }
        return posters;
    }

    List<Movie> addPoster(List<Movie> movies) {
        List<Poster> posters = parsePoster();
        for (Movie movie : movies) {
            for (Poster poster : posters) {
                if (Objects.equals(movie.getNameUkr(), poster.getMovieName())) {
                    movie.setPicturePath(poster.getPicturePath());
                }
            }
        }
        return movies;
    }


}
