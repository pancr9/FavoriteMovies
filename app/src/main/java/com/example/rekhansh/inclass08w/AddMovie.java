package com.example.rekhansh.inclass08w;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class AddMovie extends Fragment {
    AddMovieData addMovieData;
    Button b;
    boolean boolB = true;
    EditText titleAdd;
    EditText descriptionAdd;
    EditText yearAdd;
    EditText imdbAdd;
    SeekBar s;
    Spinner spinner;
    String link;
    TextView rating;

    public AddMovie() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add Movie");
        rating = (TextView) getActivity().findViewById(R.id.textView_rate2);
        titleAdd = (EditText) getActivity().findViewById(R.id.editText_name);
        descriptionAdd = (EditText) getActivity().findViewById(R.id.editText_description);
        yearAdd = (EditText) getActivity().findViewById(R.id.editText_year);
        imdbAdd = (EditText) getActivity().findViewById(R.id.editText_imdb);
        s = (SeekBar) getActivity().findViewById(R.id.seekBar_rate);
        b = (Button) getActivity().findViewById(R.id.button_addMovietoList);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner_categories);
        link = "www.imdb.com";
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner, getResources().getStringArray(R.array.genre_array)) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) tv.setTextColor(Color.GRAY);
                else tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(adapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolB = true;
                    final String a1 = titleAdd.getText().toString();
                    final String a2 = descriptionAdd.getText().toString();
                    int a4 = 0;
                    final String a5 = imdbAdd.getText().toString();
                    if (yearAdd.getText().toString().isEmpty()) boolB = false;
                    else a4 = Integer.parseInt(yearAdd.getText().toString());

                    if (a1.isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter movie name", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (a2.isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter movie description", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if ("Select".equals(spinner.getSelectedItem().toString())) {
                        Toast.makeText(getActivity(), "Please select movie genre", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (yearAdd.getText().toString().length() != 4) {
                        Toast.makeText(getActivity(), "Please enter 4 digit movie release year", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (a4 < 1895) {
                        Toast.makeText(getActivity(), "Please enter valid year", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    } else if (!a5.toLowerCase().contains(link.toLowerCase())) {
                        Toast.makeText(getActivity(), "Please enter valid link", Toast.LENGTH_SHORT).show();
                        boolB = false;
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please enter valid input", Toast.LENGTH_SHORT).show();
                    boolB = false;
                }

                if (boolB) {
                    Movie movie = new Movie(titleAdd.getText().toString(), descriptionAdd.getText().toString(), spinner.getSelectedItem().toString(), s.getProgress(), Integer.parseInt(yearAdd.getText().toString()), imdbAdd.getText().toString());
                    addMovieData.addMovieToList(movie);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_movie, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addMovieData = (AddMovieData) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface AddMovieData {
        void addMovieToList(Movie movie);
    }
}
