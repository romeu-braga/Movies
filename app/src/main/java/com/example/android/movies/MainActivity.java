package com.example.android.movies;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieItemClickListener{

    private static String CLASS_NAME = MainActivity.class.getSimpleName();
    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mAdapter;
    private Toast mToast;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private List<String> mListResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = (RecyclerView)findViewById(R.id.rv_movies);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mListResults = new ArrayList<String>();

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



        mAdapter = new MoviesAdapter(mListResults, this);
        mMoviesRecyclerView.setAdapter(mAdapter);

        makeMovieDbRequest();
    }

    @Override
    public void onMovieItemClick() {
            if(mToast != null) {
                mToast.cancel();
            }

            mToast = Toast.makeText(this, "Test", Toast.LENGTH_LONG);
            mToast.show();
    }

    private void makeMovieDbRequest() {
        URL movieDbUrlRequest = NetworkUtils.buildUrl(NetworkUtils.FILTER_POPULARITY);
        new MovieDbAsyncTask().execute(movieDbUrlRequest);
    }

    private void showMovieData() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class MovieDbAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String movieDbResultsRequest = null;

            try {
                movieDbResultsRequest = NetworkUtils.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movieDbResultsRequest;
        }

        @Override
        protected void onPostExecute(String result) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(result != null && !result.equals("")) {
                mListResults.add(result);
                mAdapter.notifyDataSetChanged();
                showMovieData();
            } else {
                showErrorMessage();
            }
        }
    }

}
