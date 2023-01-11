package com.olshevchenko.movielandparser.entity;

import lombok.Data;

/**
 * @author Oleksandr Shevchenko
 */
@Data
public class User {

    private Long id;
    private String nickName;
    private String email;
    private String password;
}
