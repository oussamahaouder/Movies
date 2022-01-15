package com.example.tp_eilco;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenreDbApi {
    @GET("list?api_key=6ebd6e20f260b3c5f75b7297acde9312")
    Call<Genres> moviesids();
}
