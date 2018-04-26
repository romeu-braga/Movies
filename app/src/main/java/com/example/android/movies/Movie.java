package com.example.android.movies;

public class Movie {

    private String mTitle;
    private String mPosterPath;
    private Integer mMovieId;

    public static String BASE_POST_IMAGE_PATH = "http://image.tmdb.org/t/p/";
    public static String POST_IMAGE_SIZE = "/w185";

    public Movie (String title, String posterPath, Integer movieId) {
        mTitle = title;
        mPosterPath = posterPath;
        mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public Integer getmMovieId() {
        return mMovieId;
    }

    public String getPostFullPath() {
        return BASE_POST_IMAGE_PATH + POST_IMAGE_SIZE + getPosterPath();
    }
}
