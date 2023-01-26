package com.olshevchenko.movielandparser.entity;

import lombok.Data;

/**
 * @author Oleksandr Shevchenko
 */
@Data
public class Review {

    private Long id;
    private Long movieId;
    private Long userId;
    private String content;
}
