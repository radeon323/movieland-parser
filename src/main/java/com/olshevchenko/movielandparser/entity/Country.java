package com.olshevchenko.movielandparser.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

/**
 * @author Oleksandr Shevchenko
 */
@Data
@Builder
public class Country {

    private Long id;
    private String name;
    private Set<Movie> movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
