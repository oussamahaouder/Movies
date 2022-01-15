package com.example.tp_eilco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {

    private static final String TAG = "MovieDetail";
    TextView textView;
    ImageView image;
    TextView overview;
    TextView textView2;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_movie_detail);

        textView=findViewById(R.id.textView);
        image=findViewById(R.id.image);
        overview=findViewById(R.id.overview);
        textView2=findViewById(R.id.textView2);
        date=findViewById(R.id.date);


        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String imagepath = intent.getStringExtra("Image");
        String Overview = intent.getStringExtra("description");
        ArrayList<Long> genres=(ArrayList<Long>)  intent.getSerializableExtra("ids"); // (Cast)=>As arrayList => Serializable (Serialisé de type Arraylist<Long>)
        String release_date=  intent.getStringExtra("date");

        date.setText( "Date de sortie : "+release_date);
        overview.setText(Overview);
        textView.setText(title);

        Picasso.with(this).load("https://image.tmdb.org/t/p/original"+imagepath).into(image);
        GenreDbApi genreDbApi = RetrofitClientGenre.getRetrofitInstanceGenres().create(GenreDbApi.class);

        Call<Genres> callG=genreDbApi.moviesids();
        callG.enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                List<String> MovieGenre = new ArrayList<>();
                ArrayList<Genres.Genre> genreName = response.body().getGenres();
                for(Long item:genres){
                    for (Genres.Genre genres1 :genreName){
                        if(item==genres1.getId()){
                            MovieGenre.add(genres1.getName());
                        }
                    }



                }
                textView2.setText ("Genres : "+MovieGenre.toString());
            }





            @Override
            public void onFailure(Call<Genres> call, Throwable t) {

            }
        });



    }
}