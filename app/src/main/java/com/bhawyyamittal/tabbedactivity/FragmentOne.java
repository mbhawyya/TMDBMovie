package com.bhawyyamittal.tabbedactivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhawyyamittal.tabbedactivity.model.Movie;
import com.bhawyyamittal.tabbedactivity.model.MovieResponse;
import com.bhawyyamittal.tabbedactivity.rest.APIClient;
import com.bhawyyamittal.tabbedactivity.rest.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BHAWYYA MITTAL on 19-11-2017.
 */

public class FragmentOne extends android.support.v4.app.Fragment {
    int pageno;
    private static final String API_KEY = "db39066d8382ee24bab946d9a4f0eba5";
    ListView listView1;
    View view;
    public FragmentOne(){}

    public static FragmentOne newInstance(int pagenum){
        FragmentOne f1 = new FragmentOne();
        Bundle bundle =new Bundle();
        bundle.putInt("arg_page",pagenum);
        f1.setArguments(bundle);
        return f1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        pageno= arguments.getInt("arg_page");
        View v = inflater.inflate(R.layout.fragone,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TextView textView = view.findViewById(R.id.textView1);
        //textView.append(" "+pageno);
        listView1 = view.findViewById(R.id.listView);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                final List<Movie> text = response.body().getResults();
                CustomAdapter adapter = new CustomAdapter(getContext(), text);
                //View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
                //listView1.addHeaderView(header);
                listView1.setAdapter(adapter);
                //Log.e("ErrorinLog","Overview is "+text);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int id = text.get(i).getId();
                        String poster = text.get(i).getBackdropPath();
                        String name = text.get(i).getTitle();
                        Log.e("MyOnClickListener", "ID is " + id + "Name is " + name);
                        Toast.makeText(getActivity().getApplicationContext(), "The id of this item is " + id + " , name is " + name, Toast.LENGTH_LONG).show();
                        Log.e("MyOnClickListener", "Trying to open the fragment details");
                        Intent intent = new Intent(getActivity().getApplicationContext(), Fragment_Details.class);
                        intent.putExtra("ID", id);
                        intent.putExtra("PosterPath", poster);
                        intent.putExtra("Title", text.get(i).getTitle());
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error in getting the response!", Toast.LENGTH_LONG).show();
            }

        });
    }
}

