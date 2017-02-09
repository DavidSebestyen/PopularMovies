package com.example.android.populatmovies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.populatmovies.utilities.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by krypt on 09/02/2017.
 */

public class DetailActivity extends Activity {

    private TextView mMovieTitle;
    private TextView mMovieOverView;
    private ImageView mMovieBackDrop;
    private TextView mMovieRelease;
    private TextView mMovieRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mMovieTitle = (TextView) findViewById(R.id.movite_title);
        mMovieOverView = (TextView) findViewById(R.id.movie_overview);
        mMovieRelease = (TextView) findViewById(R.id.movie_release);
        mMovieBackDrop = (ImageView) findViewById(R.id.detail_movie_image_view);
        mMovieRating = (TextView) findViewById(R.id.movie_rating);

        Bundle bundle = getIntent().getExtras();

        Movie movieData = bundle.getParcelable("MOVIE_DATA");
        mMovieTitle.setText(movieData.getmMovieTitle());
        mMovieOverView.setText(movieData.getmMovieOverview());
        mMovieRelease.setText("Release Date: " + movieData.getmMovieRelease());
        mMovieRating.setText("User rating: " + movieData.getmMovieRating() + " out of 10");


        Picasso.with(this).load(movieData.getmMoviePosterPath()).into(mMovieBackDrop);

    }


}
