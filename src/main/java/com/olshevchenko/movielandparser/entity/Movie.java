package com.olshevchenko.movielandparser.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author Oleksandr Shevchenko
 */

@Data
@Builder
public class Movie {

    private Long id;
    private String nameUkr;
    private String nameEng;
    private int year;
    private Set<Country> countries;
    private Set<Genre> genres;
    private String description;
    private double rating;
    private double price;
    private String picturePath;

}
