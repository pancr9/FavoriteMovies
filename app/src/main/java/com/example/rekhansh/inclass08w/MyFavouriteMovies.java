package com.example.rekhansh.inclass08w;


import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavouriteMovies extends Fragment {
    ArrayList<Movie> myMovieList = new ArrayList<>();
    Button addMovie;
    Button delMovie;
    Button editMovie;
    Button showList;
    Button showRating;
    CharSequence[] charSequenceItems = {};
    SelectedMovie selectedMovie;

    public MyFavouriteMovies() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("My Favourite Movies");

        addMovie = (Button) getActivity().findViewById(R.id.button_addMovie);
        delMovie = (Button) getActivity().findViewById(R.id.button_deleteMovie);
        editMovie = (Button) getActivity().findViewById(R.id.button_edit);
        showList = (Button) getActivity().findViewById(R.id.button_showList);
        showRating = (Button) getActivity().findViewById(R.id.button_showRating);

        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new AddMovie(), "TAG_ADDMOVIE")
                        .addToBackStack(null)
                        .commit();
            }
        });

        delMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMovieList = ((MainActivity) getActivity()).getMoviesList();
                if (myMovieList.size() != 0) {
                    List<CharSequence> listItems = new ArrayList<CharSequence>();
                    for (int i = 0; i < myMovieList.size(); i++) {
                        Movie m = myMovieList.get(i);
                        listItems.add(m.getMovieTitle());
                    }
                    charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select a Movie");
                    builder.setItems(charSequenceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String removedMovie = myMovieList.get(which).getMovieTitle();
                            selectedMovie.setSelectedMovieToDelete(which);
                            Toast.makeText(getActivity(), "Movie record: " + removedMovie + " is deleted.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    final AlertDialog radioAlert = builder.create();
                    radioAlert.show();
                } else {
                    Toast.makeText(getActivity(), "No records to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMovieList = ((MainActivity) getActivity()).getMoviesList();
                if (myMovieList.size() == 0) {
                    Toast.makeText(getActivity(), "No movies to edit", Toast.LENGTH_SHORT).show();
                } else {
                    List<CharSequence> listItems = new ArrayList<CharSequence>();
                    for (int i = 0; i < myMovieList.size(); i++) {
                        Movie m = myMovieList.get(i);
                        listItems.add(m.getMovieTitle());
                    }
                    charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Pick a Movie");
                    builder.setItems(charSequenceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("Demo", charSequenceItems[which].toString());
                            selectedMovie.setSelectedMovieToEdit(which);
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, new EditMovie(), "TAG_EDITMOVIE")
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                    final AlertDialog radioAlert = builder.create();
                    radioAlert.show();
                }
            }
        });

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMovieList = ((MainActivity) getActivity()).getMoviesList();
                if (myMovieList.size() > 0) {
                    selectedMovie.sortMoviesByYear();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new SortedMovieDetails(), "TAG_SORTEDMOVIE")
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "No movies found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMovieList = ((MainActivity) getActivity()).getMoviesList();
                if (myMovieList.size() > 0) {
                    selectedMovie.sortMoviesByRating();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new SortedMovieDetails(), "TAG_SORTEDMOVIE1")
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "No movies found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_favourite_movies, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectedMovie = (SelectedMovie) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface SelectedMovie {
        void setSelectedMovieToEdit(int position);

        void setSelectedMovieToDelete(int position);

        void sortMoviesByYear();

        void sortMoviesByRating();
    }
}
