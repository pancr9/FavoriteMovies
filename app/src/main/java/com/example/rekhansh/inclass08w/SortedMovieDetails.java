package com.example.rekhansh.inclass08w;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SortedMovieDetails extends Fragment {


    ArrayList<Movie> myList = new ArrayList<>();
    Button finish;
    EditText desc;
    ImageView first, previous, next, last;
    int indexAccessed = 0;
    TextView year, link, rating, title, genre;
    String sortedTitle = "";

    public SortedMovieDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sorted_movie_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myList = ((MainActivity) getActivity()).getMovieSorted();
        sortedTitle = ((MainActivity) getActivity()).getSortedTitle();
        getActivity().setTitle(sortedTitle);
        ((TextView) getActivity().findViewById(R.id.textView_sortType)).setText(sortedTitle);

        first = (ImageView) getActivity().findViewById(R.id.imageView_first);
        previous = (ImageView) getActivity().findViewById(R.id.imageView_previous);
        next = (ImageView) getActivity().findViewById(R.id.imageView_next);
        last = (ImageView) getActivity().findViewById(R.id.imageView_last);
        finish = (Button) getActivity().findViewById(R.id.button_finish);
        genre = (TextView) getActivity().findViewById(R.id.textView_genreDisplay);
        desc = (EditText) getActivity().findViewById(R.id.textView_described);
        year = (TextView) getActivity().findViewById(R.id.textView_displayYear);
        link = (TextView) getActivity().findViewById(R.id.textView_displayLink);
        rating = (TextView) getActivity().findViewById(R.id.textView_ratingDisplay);
        title = (TextView) getActivity().findViewById(R.id.textView_titleDisplay);

        setFields(0, myList);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexAccessed = 0;
                setFields(indexAccessed, myList);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexAccessed = indexAccessed - 1;
                if (indexAccessed < 0) {
                    Toast.makeText(getActivity(), "This is the first record", Toast.LENGTH_SHORT).show();
                    indexAccessed = 0;
                } else {
                    setFields(indexAccessed, myList);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexAccessed = indexAccessed + 1;
                if (indexAccessed >= myList.size()) {
                    indexAccessed = myList.size();
                    Toast.makeText(getActivity(), "This is the last record", Toast.LENGTH_SHORT).show();
                } else {
                    setFields(indexAccessed, myList);
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFields(myList.size() - 1, myList);
            }
        });
    }

    private void setFields(int i, ArrayList<Movie> myList) {
        title.setText(myList.get(i).getMovieTitle());
        desc.setText(myList.get(i).getMovieDescription());
        year.setText(myList.get(i).getMovieYear() + "");
        link.setText(myList.get(i).getMovieImdbLink());
        rating.setText(myList.get(i).getMovieRating() + " / 5");
        genre.setText(myList.get(i).getMovieGenre());
    }

}
