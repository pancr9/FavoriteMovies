package com.example.rekhansh.inclass08w;

import java.io.Serializable;

/**
 * Created by Rekhansh on 1/26/2017.
 */

public class Movie implements Serializable {

    private String movieTitle, movieDescription, movieGenre, movieImdbLink;
    private int movieRating, movieYear;


    public Movie(String movieTitle, String movieDescription, String movieGenre, int movieRating, int movieYear, String movieImdbLink) {
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieRating = movieRating;
        this.movieYear = movieYear;
        this.movieImdbLink = movieImdbLink;
        this.movieGenre = movieGenre;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public String getMovieImdbLink() {
        return movieImdbLink;
    }

    public String getMovieGenre() {
        return movieGenre;
    }
}

