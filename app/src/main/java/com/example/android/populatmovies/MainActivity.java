package com.example.android.populatmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.android.populatmovies.utilities.Movie;
import com.example.android.populatmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecycleView;

    private MovieAdapter mMovieAdapter;

    private static final int NUM_LIST_ITEMS = 100;

    public List<Movie> movieData;

    private static String API_KEY = "0e9cd8218337c6909070f6bbdfe4dd83";

    private static final String TOP_RATED_MOVIE_REQUEST_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key="+ API_KEY;

    private static final String MOST_POPULAR_MOVIE_REQUEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key="+ API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        FetchMovieTask task = new FetchMovieTask();
        task.execute(MOST_POPULAR_MOVIE_REQUEST_URL);

        mRecycleView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(getApplicationContext(), NUM_LIST_ITEMS, new ArrayList<Movie>(), this);

        mRecycleView.setAdapter(mMovieAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reload_data:
                reloadData();
                return true;

            case R.id.sort_by_popularity:
                sortByPopular();
                return true;

            case R.id.sort_by_rating:
                sortByRating();
                return true;

                default:
                    return super.onOptionsItemSelected(item);


        }
    }

    private void reloadData() {
        FetchMovieTask task = new FetchMovieTask();
        task.execute(MOST_POPULAR_MOVIE_REQUEST_URL);
    }

    private void sortByPopular() {
        FetchMovieTask task = new FetchMovieTask();
        task.execute(MOST_POPULAR_MOVIE_REQUEST_URL);
    }

    private void sortByRating() {
        FetchMovieTask task = new FetchMovieTask();
        task.execute(TOP_RATED_MOVIE_REQUEST_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(int clickedPosition) {
        Context context = getApplicationContext();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("MOVIE_DATA", movieData.get(clickedPosition));
        startActivity(intent);

    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Movie> result = NetworkUtils.fetchMovieData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> data) {
            if (data != null && !data.isEmpty()) {
                mMovieAdapter.setMoviePoster(null);
                mMovieAdapter.setMoviePoster(data);
                // Put list data into a new list that will be send to the intent
                movieData = new ArrayList<Movie>();
                movieData = data;

            } else {
                Log.e("Data", "No data");
            }
        }
    }
}
