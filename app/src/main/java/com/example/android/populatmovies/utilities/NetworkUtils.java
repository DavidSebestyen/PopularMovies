package com.example.android.populatmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by krypt on 08/02/2017.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "We have a problem to build the URL", e);
        }
        return url;
    }
    
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Movie> getMovieDBJson (String movieJsonStr){

        final String MDB_RESULTS = "results";

        final String MDB_POSTER_PATH = "poster_path";

        final String MDB_TITLE = "original_title";

        final String MDB_RATING = "vote_average";

        final String MDB_OVERVIEW = "overview";

        final String MDB_RELEASE = "release_date";

        final String MDB_BACKDROP_IMAGE = "backdrop_path";



        List<Movie> movies = new ArrayList<>();

        try {
            if (movieJsonStr != null) {

                JSONObject movieJson = new JSONObject(movieJsonStr);

                JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

                for(int i = 0; i <= movieArray.length(); i++){
                    JSONObject movie = movieArray.getJSONObject(i);

                    String posterPath = movie.getString(MDB_POSTER_PATH);
                    posterPath = "http://image.tmdb.org/t/p/w342/" + posterPath;

                    String title = movie.getString(MDB_TITLE);

                    String rating = movie.getString(MDB_RATING);

                    String overview = movie.getString(MDB_OVERVIEW);

                    String release = movie.getString(MDB_RELEASE);

                    String backdropPath = movie.getString(MDB_BACKDROP_IMAGE);
                    backdropPath = "http://image.tmdb.org/t/p/w342/" + backdropPath;


                    Movie film = new Movie(posterPath, title, overview, release, rating, backdropPath);

                    movies.add(film);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;


    }

    public static List<Movie> fetchMovieData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Movie> movies = getMovieDBJson(jsonResponse);

        return movies;
    }
}
