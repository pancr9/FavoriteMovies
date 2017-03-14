package com.example.rekhansh.inclass08w;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditMovie extends Fragment {

    EditMovie.editedmovie editedmovie;

    boolean boolB;
    Button b;
    EditText titleAdd, descriptionAdd, yearAdd, imdbAdd;
    int yearLen;
    Movie m;
    SeekBar s;
    Spinner spinner;
    String link, s1, s2, s3;
    TextView rating;

    public EditMovie() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Edit Movie");

        //Find views
        rating = (TextView) getActivity().findViewById(R.id.textView_rate2);
        titleAdd = (EditText) getActivity().findViewById(R.id.editText_name);
        descriptionAdd = (EditText) getActivity().findViewById(R.id.editText_description);
        yearAdd = (EditText) getActivity().findViewById(R.id.editText_year);
        imdbAdd = (EditText) getActivity().findViewById(R.id.editText_imdb);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner_categories);
        s = (SeekBar) getActivity().findViewById(R.id.seekBar_rate);
        b = (Button) getActivity().findViewById(R.id.button_addMovietoList);
        link = "www.imdb.com";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.genre_array_edit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rating.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        m = ((MainActivity) getActivity()).getSelectedMovie();
        titleAdd.setText(m.getMovieTitle());
        descriptionAdd.setText(m.getMovieDescription());
        yearAdd.setText(m.getMovieYear() + "");
        imdbAdd.setText(m.getMovieImdbLink());
        spinner.setSelection(getIndex(spinner, m.getMovieGenre()));
        s.setProgress(m.getMovieRating());
        rating.setText(m.getMovieRating() + "");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolB = true;
                String text = spinner.getSelectedItem().toString();

                try {
                    boolB = true;
                    s1 = titleAdd.getText().toString();
                    s2 = descriptionAdd.getText().toString();
                    s3 = imdbAdd.getText().toString();
                    yearLen = 0;
                    if (yearAdd.getText().toString().isEmpty()) boolB = false;
                    else yearLen = Integer.parseInt(yearAdd.getText().toString());

                    if (s1.isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter movie name", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (s2.isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter movie description", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (yearAdd.getText().toString().length() != 4) {
                        Toast.makeText(getActivity(), "Please enter 4 digit movie release year", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (yearLen < 1895) {
                        Toast.makeText(getActivity(), "Please enter valid year", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (!s3.toLowerCase().contains(link.toLowerCase())) {
                        Toast.makeText(getActivity(), "Please enter valid link", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please enter valid input", Toast.LENGTH_SHORT).show();
                    boolB = false;
                }
                if (boolB) {
                    Movie movie = new Movie(titleAdd.getText().toString(), descriptionAdd.getText().toString(), text, s.getProgress(), Integer.parseInt(yearAdd.getText().toString()), imdbAdd.getText().toString());
                    editedmovie.setEditedMovie(movie);
                    getActivity().onBackPressed();
                }
            }
        });
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            Log.d("demo", "Spinner : " + spinner.getItemAtPosition(i).toString());
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        Log.d("demo", "Spinner returns : " + index);
        return index;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editedmovie = (EditMovie.editedmovie) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface editedmovie {
        void setEditedMovie(Movie movie);
    }
}
