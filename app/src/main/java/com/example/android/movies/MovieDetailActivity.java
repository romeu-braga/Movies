package com.example.android.movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.movies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie mMovie;

    private TextView mMovieTitleTextView;
    private ImageView mMovieDetailPost;

    private TextView mMovieReleaseDateTextView;
    private TextView mMovieVoteAverageTextView;
    private TextView mMovieOverviewTextView;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mMovieDetailPost = (ImageView) findViewById(R.id.iv_movie_detail_post);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_movie_detail);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_movie_detail);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date);
        mMovieVoteAverageTextView = (TextView) findViewById(R.id.tv_movie_vote_average);
        mMovieOverviewTextView = (TextView) findViewById(R.id.tv_movie_overview);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mMovie = null;
        } else {
            mMovie = (Movie) extras.getParcelable(MainActivity.PUT_EXTRA_MOVIE_ID);
        }

        makeMovieDetailRequest(mMovie.getmMovieId());

        mErrorMessageDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMovieDetailRequest(mMovie.getmMovieId());
            }
        });
    }

    private void makeMovieDetailRequest(Integer movieId) {
        if(NetworkUtils.isOnline(this)) {
            URL movieDetailUrlRequest = NetworkUtils.buildUrl(movieId);
            new MovieDetailAsyncTask().execute(movieDetailUrlRequest);
        }
    }

    private void showMovieData() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMovieTitleTextView.setVisibility(View.VISIBLE);
        mMovieDetailPost.setVisibility(View.VISIBLE);
        mMovieReleaseDateTextView.setVisibility(View.VISIBLE);
        mMovieVoteAverageTextView.setVisibility(View.VISIBLE);
        mMovieOverviewTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMovieTitleTextView.setVisibility(View.INVISIBLE);
        mMovieDetailPost.setVisibility(View.INVISIBLE);
        mMovieReleaseDateTextView.setVisibility(View.INVISIBLE);
        mMovieVoteAverageTextView.setVisibility(View.INVISIBLE);
        mMovieOverviewTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class MovieDetailAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];

            String movieDetailResultsRequest = null;

            try {
                movieDetailResultsRequest = NetworkUtils.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movieDetailResultsRequest;
        }

        @Override
        protected void onPostExecute(String result) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if(result != null && !result.equals("")) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String title = jsonObject.getString("title");
                    String postPath = jsonObject.getString("poster_path");
                    String releaseDate = jsonObject.getString("release_date");
                    String voteAverage = jsonObject.getString("vote_average");
                    String overview = jsonObject.getString("overview");

                    setTitle(title);
                    mMovieTitleTextView.setText(title);
                    Picasso.with(MovieDetailActivity.this).load(mMovie.getPostFullPath()).into(mMovieDetailPost);
                    mMovieReleaseDateTextView.setText(releaseDate);
                    mMovieVoteAverageTextView.setText(voteAverage);
                    mMovieOverviewTextView.setText(overview);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                showMovieData();

            } else {
                showErrorMessage();
            }

        }

    }

}
