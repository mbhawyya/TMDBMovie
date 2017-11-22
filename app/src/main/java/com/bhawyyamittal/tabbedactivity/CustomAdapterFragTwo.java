package com.bhawyyamittal.tabbedactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhawyyamittal.tabbedactivity.model.Genre;
import com.bhawyyamittal.tabbedactivity.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BHAWYYA MITTAL on 19-11-2017.
 */

public class CustomAdapterFragTwo extends ArrayAdapter<Movie> {
    int img;
    List<Movie> movie;
    String genreString;
    Context context;
    public String BASE_URL = "http://image.tmdb.org/t/p/w500/";
    public CustomAdapterFragTwo(@NonNull Context cont, List<Movie> mov) {
        super(cont, R.layout.row, mov);
        //  this.img = imgs;
        this.movie = mov;
        this.context = cont;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.row,parent,false);
        TextView t1 = row.findViewById(R.id.firstLine);
        TextView t2 = row.findViewById(R.id.secondLine);
        ImageView i1 = row.findViewById(R.id.icon);
        //t1.setText(movie.get(position) +" " +movie.get(position).getVoteAverage());
        t1.setText(movie.get(position).getReleaseDate().substring(0,4));
        t2.setText(movie.get(position).getTitle());
         String posterpath = movie.get(position).getPosterPath();
        String url = BASE_URL+posterpath;
        Picasso.with(context).load(url).into(i1);

        return row;
    }
}
