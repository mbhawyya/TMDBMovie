package com.bhawyyamittal.tabbedactivity.rest;

import com.bhawyyamittal.tabbedactivity.IMDBResponse;;
import com.bhawyyamittal.tabbedactivity.model.Movie;
import com.bhawyyamittal.tabbedactivity.model.MovieResponse;
import com.bhawyyamittal.tabbedactivity.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by BHAWYYA MITTAL on 18-11-2017.
 */

public interface APIInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> fetchPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoResponse> fetchYoutubeTrailer(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("tadas-subonis/imdb-top-250/data.json")
    Call<IMDBResponse> getIMDBTop(@Query("key") String apiKey, @Query("query") String query);


}
