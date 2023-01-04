package com.olshevchenko.movielandparser.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Oleksandr Shevchenko
 */
@Entity
@Setter
@ToString
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_id_gen")
    @SequenceGenerator(name = "movies_id_gen", sequenceName = "movies_id_seq")
    private Long id;

    @Column(name = "name_ukr")
    private String nameUkr;

    @Column(name = "name_eng")
    private String nameEng;

    private int year;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_countries",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "country_id")}
            ,
            uniqueConstraints = {@UniqueConstraint(
            columnNames = {"movie_id", "country_id"})}
    )
    private Set<Country> countries;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_genres",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
            ,
            uniqueConstraints = {@UniqueConstraint(
                    columnNames = {"movie_id", "genre_id"})}
    )
    private Set<Genre> genres;

    @Column(columnDefinition="TEXT")
    private String description;

    private double rating;

    private double price;
}
