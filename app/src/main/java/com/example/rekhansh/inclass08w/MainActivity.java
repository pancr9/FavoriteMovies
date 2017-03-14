package com.example.rekhansh.inclass08w;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AddMovie.AddMovieData,
        MyFavouriteMovies.SelectedMovie, EditMovie.editedmovie {

    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Movie> movieSorted = new ArrayList<>();
    Movie selectedMovie;
    Movie editedMovie;
    String sortTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        getFragmentManager().beginTransaction()
                .add(R.id.container, new MyFavouriteMovies(), "TAG_MYFAVORITEMOVIES")
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void addMovieToList(Movie movie) {
        movies.add(movie);
        onBackPressed();
    }

    @Override
    public void setSelectedMovieToEdit(int postion) {
        selectedMovie = movies.get(postion);
        movies.remove(postion);
    }

    @Override
    public void setSelectedMovieToDelete(int position) {
        movies.remove(position);
    }

    @Override
    public void sortMoviesByYear() {
        movieSorted = (ArrayList<Movie>) movies.clone();
        sortTitle = "Movies By Year";
        Collections.sort(movieSorted, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.getMovieYear() >= o2.getMovieYear()) return 1;
                else return -1;
            }
        });
    }

    @Override
    public void sortMoviesByRating() {
        movieSorted = (ArrayList<Movie>) movies.clone();
        sortTitle = "Movies By Rating";
        Collections.sort(movieSorted, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.getMovieRating() >= o2.getMovieRating()) return -1;
                else return 1;
            }
        });
    }

    @Override
    public void setEditedMovie(Movie movie) {
        editedMovie = movie;
        movies.add(editedMovie);
    }

    public ArrayList<Movie> getMoviesList() {
        return movies;
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public ArrayList<Movie> getMovieSorted() {
        return movieSorted;
    }

    public String getSortedTitle() {
        return sortTitle;
    }
}
