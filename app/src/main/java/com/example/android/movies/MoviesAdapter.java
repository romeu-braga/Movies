package com.example.android.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private static final String CLASS_NAME = MoviesAdapter.class.getSimpleName();
    final private MovieItemClickListener mOnClickListener;
    private List<String> mMoviesList;

    public MoviesAdapter(List<String> moviesList, MovieItemClickListener listener) {
        mOnClickListener = listener;
        mMoviesList = moviesList;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachtoParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachtoParentImmediately);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mMoviesList == null) {
            return 0;
        }
        return mMoviesList.size();
    }

    public interface MovieItemClickListener {
        void onMovieItemClick();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieItemImage;
        TextView movieItemText;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            movieItemImage = (ImageView) itemView.findViewById(R.id.iv_movie_item);
            movieItemText = (TextView) itemView.findViewById(R.id.tv_movie_item);
            itemView.setOnClickListener(this);
        }

        void bind(int i) {
            Picasso.with(itemView.getContext()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(movieItemImage);
            movieItemText.setText(mMoviesList.get(i));
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onMovieItemClick();
        }
    }



}
