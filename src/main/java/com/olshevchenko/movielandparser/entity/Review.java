package com.olshevchenko.movielandparser.entity;

import lombok.Data;

/**
 * @author Oleksandr Shevchenko
 */
@Data
public class Review {

    private Long id;
    private String movieName;
    private String userNickName;
    private String content;
}
