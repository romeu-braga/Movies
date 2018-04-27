package com.example.android.movies;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieItemClickListener{

    private static String CLASS_NAME = MainActivity.class.getSimpleName();
    public static String PUT_EXTRA_MOVIE_ID = "MOVIE_ID";
    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mAdapter;
    private Toast mToast;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private List<Movie> mListResults;
    private Intent mDetailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = (RecyclerView)findViewById(R.id.rv_movies);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mListResults = new ArrayList<Movie>();

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
    public void onMovieItemClick(Integer movieId) {
        mDetailIntent = new Intent(this, MovieDetailActivity.class);
        mDetailIntent.putExtra(PUT_EXTRA_MOVIE_ID, movieId);
        startActivity(mDetailIntent);
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
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray moviesResults = jsonObject.getJSONArray("results");

                    for(int i = 0; i < moviesResults.length(); i++) {
                        mListResults.add(new Movie(
                                moviesResults.getJSONObject(i).getString("title"),
                                moviesResults.getJSONObject(i).getString("poster_path"),
                                moviesResults.getJSONObject(i).getInt(  "id")
                        ));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();
                showMovieData();

            } else {
                showErrorMessage();
            }
        }
    }

}
