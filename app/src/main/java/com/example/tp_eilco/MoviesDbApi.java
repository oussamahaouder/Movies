package com.example.tp_eilco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;


public interface MoviesDbApi {
    @GET("?api_key=6ebd6e20f260b3c5f75b7297acde9312")
    Call<Movies> getAllData();





}