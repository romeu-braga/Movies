package com.example.android.movies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.example.android.movies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    
    /**
    *Please set the KEY_VALUE
    */
    private static String KEY_VALUE = BuildConfig.api_key;

    private static String BASE_URL = "https://api.themoviedb.org/3";
    private static String PATH_MOVIE = "movie";

    private static String KEY_PARAMETER = "api_key";
    
    public static String FILTER_POPULARITY = "popular";
    public static String FILTER_TOP_RATED = "top_rated";

    public static String PAGE_PARAMETER = "page";

    public static URL buildUrl(String resultsType, Integer pageNumber) {
        Uri builtUri = null;

        if (resultsType.equals(FILTER_POPULARITY)) {
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(PATH_MOVIE)
                    .appendPath(FILTER_POPULARITY)
                    .appendQueryParameter(PAGE_PARAMETER, pageNumber.toString())
                    .appendQueryParameter(KEY_PARAMETER, KEY_VALUE)
                    .build();
        } else if (resultsType.equals(FILTER_TOP_RATED)) {
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(PATH_MOVIE)
                    .appendPath(FILTER_TOP_RATED)
                    .appendQueryParameter(PAGE_PARAMETER, pageNumber.toString())
                    .appendQueryParameter(KEY_PARAMETER, KEY_VALUE)
                    .build();
        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrl(Integer movieId) {
        Uri builtUri = null;

        builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(PATH_MOVIE)
                .appendPath(movieId.toString())
                .appendQueryParameter(KEY_PARAMETER, KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
