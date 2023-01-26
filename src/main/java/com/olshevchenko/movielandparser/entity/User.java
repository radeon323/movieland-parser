package com.olshevchenko.movielandparser.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Oleksandr Shevchenko
 */
@Data
@Builder
public class User {

    private Long id;
    private String nickName;
    private String email;
    private String password;
    private Role role;
}
