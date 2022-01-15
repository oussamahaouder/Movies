package com.example.tp_eilco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMoviesListner{
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mBottomNavigationView=findViewById(R.id.activity_main_bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_upcoming:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        return true;
                    case R.id.action_new:
                        Intent intent1=new Intent(getApplicationContext(),Upcoming.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent1);

                        return true;
                    case R.id.action_search:
                        Intent intent2 = new Intent(getApplicationContext(),SearchActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent2);
                        return true;
                }
                return false;
            }
        });
        recyclerView=findViewById(R.id.movies);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        MoviesDbApi moviesDbApi = RetrofitClient.getRetrofitInstance().create(MoviesDbApi.class);
        Call<Movies> call =moviesDbApi.getAllData();


        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                ArrayList<Movies.data> data = response.body().getResults();
                for (Movies.data data1 : data) {

                    adapter = new MoviesAdapter(data,MainActivity.this,MainActivity.this);
                    recyclerView.setAdapter(adapter);

                }


                    }
                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage() );

                    }
                });

        }


    ;


    @Override
    public void onMovieClick(ArrayList<Movies.data> movie, int position) {


        Intent intent = new Intent(this,MovieDetail.class);

        int idMovie = movie.get(position).id;
        String MovieName=movie.get(position).title;
        String Image = movie.get(position).poster_path;
        String Desc =movie.get(position).overview;
        ArrayList<Long> genre= movie.get(position).genre_ids; // [Values,,,,,,n] ArrayList
        String releasedate= movie.get(position).release_date;

        intent.putExtra("date",releasedate);
        intent.putExtra("ids",  genre);
        intent.putExtra("idMovie",idMovie);
        intent.putExtra("Title",MovieName);
        intent.putExtra("Image",Image);
        intent.putExtra("description",Desc);
        startActivity(intent);

    }
  
}



