package com.example.android.movies;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.movies.Utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieItemClickListener{

    private static String CLASS_NAME = MainActivity.class.getSimpleName();
    private static final int NUM_LIST_ITEMS = 100;
    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = (RecyclerView)findViewById(R.id.rv_movies);

        GridLayoutManager gridLayoutManager = null;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager = new GridLayoutManager(this, 2);
        }
        else{
            gridLayoutManager = new GridLayoutManager(this, 3);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);

        mMoviesRecyclerView.setHasFixedSize(true);

        mAdapter = new MoviesAdapter(NUM_LIST_ITEMS, this);
        mMoviesRecyclerView.setAdapter(mAdapter);


        Log.d(CLASS_NAME, NetworkUtils.buildUrl(NetworkUtils.FILTER_POPULARITY).toString());
        Log.d(CLASS_NAME, NetworkUtils.buildUrl(NetworkUtils.FILTER_HIGHEST_RATED).toString());

    }



    @Override
    public void onMovieItemClick() {

            if(mToast != null) {
                mToast.cancel();
            }

            mToast = Toast.makeText(this, "Test", Toast.LENGTH_LONG);
            mToast.show();
    }

}
