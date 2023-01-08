package com.olshevchenko.movielandparser.entity;

import lombok.Data;

/**
 * @author Oleksandr Shevchenko
 */
@Data
public class Review {

    private Long id;
    private String movieName;
    private String userFirstName;
    private String userLastName;
    private String content;
}
