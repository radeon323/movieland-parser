package com.olshevchenko.movielandparser.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * @author Oleksandr Shevchenko
 */
@Data
@Builder
public class Poster {

    private Long id;
    private String movieName;
    private String picturePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poster poster = (Poster) o;
        return Objects.equals(picturePath, poster.picturePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picturePath);
    }
}
