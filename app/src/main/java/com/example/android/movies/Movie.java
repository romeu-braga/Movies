package com.example.android.movies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                this.mTitle,this.mPosterPath,this.mMovieId.toString()
        });
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };

    private Movie (Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);

        this.mTitle = data[0];
        this.mPosterPath = data[1];
        this.mMovieId = Integer.parseInt(data[2]);

    }
}
