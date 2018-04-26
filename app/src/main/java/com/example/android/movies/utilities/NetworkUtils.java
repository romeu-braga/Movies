package com.example.android.movies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static String BASE_URL = "https://api.themoviedb.org/3";
    private static String PATH_DISCOVER = "discover";
    private static String PATH_MOVIE = "movie";

    private static String SORT_PARAMETER = "sort_by";
    private static String SORT_POPULARITY_VALUE = "popularity.desc";

    private static String CERTIFICATION_COUNTRY_PARAMETER = "certification_country";
    private static String CERTIFICATION_COUNTRY_VALUE = "US";
    private static String CERTIFICATION_PARAMETER = "certification";
    private static String CERTIFICATION_VALUE = "R";

    private static String SORT_HIGHEST_RATED_VALUE = "vote_average.desc";

    private static String KEY_PARAMETER = "api_key";
    private static String KEY_VALUE = "7ecc0f8c451803bfb36358ee6f18e199";

    public static String FILTER_POPULARITY = "popularity";
    public static String FILTER_HIGHEST_RATED = "highest_rated";

    public static URL buildUrl(String resultsType) {
        Uri builtUri = null;

        if (resultsType.equals(FILTER_POPULARITY)) {
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(PATH_DISCOVER)
                    .appendPath(PATH_MOVIE)
                    .appendQueryParameter(SORT_PARAMETER, SORT_POPULARITY_VALUE)
                    .appendQueryParameter(KEY_PARAMETER, KEY_VALUE)
                    .build();
        } else if (resultsType.equals(FILTER_HIGHEST_RATED)) {
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(PATH_DISCOVER)
                    .appendPath(PATH_MOVIE)
                    .appendQueryParameter(CERTIFICATION_COUNTRY_PARAMETER, CERTIFICATION_COUNTRY_VALUE)
                    .appendQueryParameter(CERTIFICATION_PARAMETER, CERTIFICATION_VALUE)
                    .appendQueryParameter(SORT_PARAMETER, SORT_HIGHEST_RATED_VALUE)
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

}
