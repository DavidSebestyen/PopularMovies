package com.example.android.populatmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.populatmovies.utilities.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krypt on 08/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Context context;

    private final MovieAdapterOnClickHandler mClickHandler;

    private List<Movie> moviesList;

    private List<Movie> movieClicked;

    private List<Movie> mMovies;

    public interface MovieAdapterOnClickHandler {

        void onClick(int clickedItemIndex);

    }



    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mPosterImageView;

        public MovieAdapterViewHolder(View view){
            super(view);
            mPosterImageView = (ImageView) view.findViewById(R.id.movie_image_view);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }


    }

    private int mNumOfItems;
    public MovieAdapter (Context context, int numberOfItems, ArrayList<Movie> movies, MovieAdapterOnClickHandler handler){

        this.context = context;
        mMovies = movies;
        mNumOfItems = numberOfItems;
        mClickHandler = handler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Movie movie = mMovies.get(position);

        ImageView imageView = holder.mPosterImageView;

        Picasso.with(context).load(movie.getmMoviePosterPath()).into(imageView);

    }

    @Override
    public int getItemCount() {
        mNumOfItems = mMovies.size();
        return mNumOfItems;
    }

    public void setMoviePoster (List<Movie> movieData){
        mMovies = movieData;

        notifyDataSetChanged();

        movieClicked = mMovies;
    }


}
