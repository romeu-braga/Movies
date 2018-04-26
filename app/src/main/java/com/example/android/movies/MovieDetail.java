package com.example.android.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetail extends AppCompatActivity {

    private Integer mMovieId;
    private TextView mMovieIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieIdTextView = (TextView) findViewById(R.id.tv_movie_id);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mMovieId = null;
        } else {
            mMovieId = extras.getInt(MainActivity.PUT_EXTRA_MOVIE_ID);
        }

        mMovieIdTextView.setText(mMovieId.toString());
    }
}
