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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BHAWYYA MITTAL on 19-11-2017.
 */

public class FragmentTwo extends android.support.v4.app.Fragment {
    int pageno;
    private static final String API_KEY = "db39066d8382ee24bab946d9a4f0eba5";
    ListView listView2;
    View view;
    int id;
    public FragmentTwo(){}

    public static FragmentTwo newInstance(int pagenum){
        FragmentTwo f2 = new FragmentTwo();
        Bundle bundle =new Bundle();
        bundle.putInt("arg_page",pagenum);
        f2.setArguments(bundle);
        return f2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        pageno= arguments.getInt("arg_page");
        View v = inflater.inflate(R.layout.fragtwo,container,false);
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
        listView2 = view.findViewById(R.id.listView);
        APIInterface apiInterfacetwo = APIClient.getClient().create(APIInterface.class);
        Call<MovieResponse> call = apiInterfacetwo.fetchPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e("Error in Response","Response code is "+response.code() + response.errorBody());
                if(response.body()!=null) {
                    Log.e("Error in Response","Response code is "+response.code() + response.errorBody());
                    final List<Movie> text = response.body().getResults();
                    CustomAdapterFragTwo adapter = new CustomAdapterFragTwo(getContext(), text);
                    listView2.setAdapter(adapter);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            int id = text.get(i).getId();
                            String poster = text.get(i).getBackdropPath();
                            String name = text.get(i).getTitle();
                            Log.e("MyOnClickListener","ID is "+id+"Name is "+name);
                            Toast.makeText(getActivity().getApplicationContext(),"The id of this item is " + id + " , name is " + name,Toast.LENGTH_LONG).show();
                            Log.e("MyOnClickListener","Trying to open the fragment details");
                            Intent intent = new Intent(getActivity().getApplicationContext(),Fragment_Details.class);
                            intent.putExtra("ID",id);
                            intent.putExtra("PosterPath",poster);
                            intent.putExtra("Title",text.get(i).getTitle());
                            startActivity(intent);
                           // FragmentTransaction fragmentTransaction = getFragmentManager()
                              //      .beginTransaction();
                            //Fragment detailFragment = Fragment_Details.newInstanceDetails(String.valueOf(id));
                            //detailFragment.setArguments(bundle);
                            //fragmentTransaction
                              //      .replace(R.id.fragmentdetails, detailFragment);
                            //fragmentTransaction.addToBackStack(null);
                            //fragmentTransaction.commit();
                          //  Fragment_Details fg = Fragment_Details.newInstanceDetails(String.valueOf(id));


                        }
                    });

                    Log.e("ErrorinLog", "Overview is " + text);
                }else{
                    Toast.makeText(getContext(),"Null response",Toast.LENGTH_LONG).show();
                    Log.e("ErrorinResponse","Null response");
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Error in getting the response!",Toast.LENGTH_LONG).show();

            }
        });
    }
}
