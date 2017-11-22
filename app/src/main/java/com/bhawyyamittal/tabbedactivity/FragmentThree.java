package com.bhawyyamittal.tabbedactivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bhawyyamittal.tabbedactivity.model.Movie;
import com.bhawyyamittal.tabbedactivity.model.MovieResponse;
import com.bhawyyamittal.tabbedactivity.rest.APIClient;
import com.bhawyyamittal.tabbedactivity.rest.APIInterface;
import com.bhawyyamittal.tabbedactivity.rest.MorphClient;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BHAWYYA MITTAL on 19-11-2017.
 */

public class FragmentThree extends android.support.v4.app.Fragment {
    int pageno;
    private static final String API_KEY = "Iow0tybIh/A5rWQWexYi";
    String query = "select%20*%20from%20data";
    ListView listView3;
    View view;
    int id;
    public FragmentThree(){}

    public static FragmentThree newInstance(int pagenum){
        FragmentThree f3 = new FragmentThree();
        Bundle bundle =new Bundle();
        bundle.putInt("arg_page",pagenum);
        f3.setArguments(bundle);
        return f3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        pageno= arguments.getInt("arg_page");
        View v = inflater.inflate(R.layout.fragthree,container,false);
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
        listView3 = view.findViewById(R.id.listView3);
        APIInterface apiInterfacethree = MorphClient.getClient().create(APIInterface.class);
        Call<IMDBResponse> call = apiInterfacethree.getIMDBTop(API_KEY,query);
        call.enqueue(new Callback<IMDBResponse>() {

            @Override
            public void onResponse(Call<IMDBResponse> call, Response<IMDBResponse> response) {
                Log.e("Error in Response","Response code is "+response.code() + response.errorBody());
                if(response.body()!=null) {
                    Log.e("Error in Response","Response code is "+response.code() + response.errorBody());
                   // final List<Movie> text = response.body().getName();
                    final List<IMDBTop> imdb = response.body().getResponse();
                    CustomAdapterFragThree adapter = new CustomAdapterFragThree(getContext(), imdb);
                    listView3.setAdapter(adapter);
                    listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           int id = Integer.parseInt(imdb.get(i).getRating());
                           /* String poster = text.get(i).getBackdropPath();
                            String name = text.get(i).getTitle(); */
                            Log.e("MyOnClickListener","ID is "+id);
                            Toast.makeText(getActivity().getApplicationContext(),"The id of this item is " + id ,Toast.LENGTH_LONG).show();
                            Log.e("MyOnClickListener","Trying to open the fragment details");
                          /*  Intent intent = new Intent(getActivity().getApplicationContext(),Fragment_Details.class);
                            intent.putExtra("ID",id);
                            intent.putExtra("PosterPath",poster);
                            intent.putExtra("Title",text.get(i).getTitle());
                            startActivity(intent); */

                        }
                    });

                    Log.e("ErrorinLog", "Overview is " + imdb);
                }else{
                    Toast.makeText(getContext(),"Null response",Toast.LENGTH_LONG).show();
                    Log.e("ErrorinResponse","Null response");
                }

            }

            @Override
            public void onFailure(Call<IMDBResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Error in getting the response!",Toast.LENGTH_LONG).show();

            }
        });
    }
}
