package com.example.android.populatmovies.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krypt on 08/02/2017.
 */

public class Movie implements Parcelable {

    private String mMoviePosterPath;
    private String mMovieTitle;
    private String mMovieOverview;
    private String mMovieRelease;
    private String mMovieRating;
    private String mMovieBackDropPath;

    public Movie (String moviePosterPath, String movieTitle, String movieOverview, String movieRelease,
                  String movieRating, String movieBackdropPath){

        mMoviePosterPath = moviePosterPath;
        mMovieTitle = movieTitle;
        mMovieOverview = movieOverview;
        mMovieRelease = movieRelease;
        mMovieRating = movieRating;
        mMovieBackDropPath = movieBackdropPath;
    }

    public String getmMoviePosterPath() {
        return mMoviePosterPath;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public String getmMovieRelease() {
        return mMovieRelease;
    }

    public String getmMovieRating() {
        return mMovieRating;
    }

    public String getmMovieBackDropPath() {
        return mMovieBackDropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mMoviePosterPath);
        dest.writeString(mMovieTitle);
        dest.writeString(mMovieOverview);
        dest.writeString(mMovieRelease);
        dest.writeString(mMovieRating);
        dest.writeString(mMovieBackDropPath);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

            public Movie (Parcel in){
                mMoviePosterPath = in.readString();
                mMovieTitle = in.readString();
                mMovieOverview = in.readString();
                mMovieRelease = in.readString();
                mMovieRating = in.readString();
                mMovieBackDropPath = in.readString();
            }
}
