package com.bhawyyamittal.tabbedactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhawyyamittal.tabbedactivity.model.Movie;
import com.bhawyyamittal.tabbedactivity.model.Video;
import com.bhawyyamittal.tabbedactivity.model.VideoResponse;
import com.bhawyyamittal.tabbedactivity.rest.APIClient;
import com.bhawyyamittal.tabbedactivity.rest.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BHAWYYA MITTAL on 19-11-2017.
 */

public class Fragment_Details extends AppCompatActivity {
    View v;
    String id;
    TextView textView,t2;
    ImageView imgView;
    List<Video> list;
    Button b1,b2;
    String poster;
    String title;
    private static final String API_KEY = "db39066d8382ee24bab946d9a4f0eba5";
    //private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    public String BASE_URL_POSTER = "http://image.tmdb.org/t/p/w780/";

   // https://api.themoviedb.org/3/movie/{id}/videos?api_key=db39066d8382ee24bab946d9a4f0eba5

    //public Fragment_Details(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        imgView = findViewById(R.id.imgView);
        textView = findViewById(R.id.txtdetails);
        t2 = findViewById(R.id.txtTitle);
        textView.setMovementMethod(new ScrollingMovementMethod());
        b1 = findViewById(R.id.btnVideo);
        b2 = findViewById(R.id.btnShare);
        Intent intentTwo = getIntent();
        Bundle b = intentTwo.getExtras();
        if (b != null) {
            id = String.valueOf(b.getInt("ID"));
            poster = b.getString("PosterPath");
            title = b.getString("Title");
        }
        String url = BASE_URL_POSTER+poster;
        Picasso.with(this).load(url).into(imgView);
        Log.e("IntentBundle", "ID is " + id);
        t2.setText(title);
        APIInterface apiInterfacethree = APIClient.getClient().create(APIInterface.class);
        Call<Movie> call = apiInterfacethree.getMovieDetails(id, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    textView.setText(response.body().getOverview());

                } else {
                    Toast.makeText(Fragment_Details.this, "Response is null", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Error in Networking", "HTTP call failed!");
                Toast.makeText(Fragment_Details.this, "HTTP call failed!", Toast.LENGTH_LONG).show();

            }
        });


        Call<VideoResponse> callVideo = apiInterfacethree.fetchYoutubeTrailer(id, API_KEY);
        callVideo.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                list = response.body().getYoutubeTrailers();
                for (Video v : list) {
                    if (v.isYoutubeTrailer()) {

                    }

                }

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = getFirstTrailerUri();

                Log.d("Youtube Trailer", "Play url: " + url);
                startActivity(new Intent(Intent.ACTION_VIEW, url));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareVideoUrl();
            }
        });
    }
    private void shareVideoUrl() {

        Uri url = getFirstTrailerUri();
        if (url != null) {
            Intent shareIntent = new Intent();
            shareIntent.setType("text/plain");
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing this video");
            shareIntent.putExtra(Intent.EXTRA_TEXT, url.toString());
            startActivity(Intent.createChooser(shareIntent, "Share "+title));
        } else {
            Toast.makeText(Fragment_Details.this, "Sorry! There is no trailer associated with this movie!", Toast.LENGTH_LONG).show();
        }
    }

        public Uri getFirstTrailerUri() {
            return !list.isEmpty() ? list.get(0).getYoutubUrl() : null;
        }
    }















    /*public static Fragment_Details newInstanceDetails(String id){
        Fragment_Details details = new Fragment_Details();
        Log.e("MyOnClickListener","Creating aa new fragment using " + id);
        Bundle bundle =new Bundle();
        bundle.putString("id_movie",id);
        details.setArguments(bundle);
        return details;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        id= arguments.getString("id");
        Log.e("MyOnClickListener","Trying to create a view");
        v = inflater.inflate(R.layout.details,container,false);
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.v = view;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView t1 = v.findViewById(R.id.txtdetails);
        t1.setText("Fragment Working");
        Log.e("MyOnClickListener","Its'working , inside activity created");
        APIInterface apiInterfacedetails = APIClient.getClient().create(APIInterface.class);
        Call<Movie> call = apiInterfacedetails.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                // Log.e("Error in Response","Response code is "+response.code() + response.errorBody());
                if (response.body() != null) {
                    Log.e("Error in Response", "Response code is " + response.code() + response.errorBody());
                    String overview = response.body().getOverview();
                    t1.append(overview);


                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    } */
//}
