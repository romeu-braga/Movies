package com.example.android.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private static final String CLASS_NAME = MoviesAdapter.class.getSimpleName();
    final private MovieItemClickListener mOnClickListener;
    private int mNumberItems;

    public MoviesAdapter(int numberOfItems, MovieItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
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
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public interface MovieItemClickListener {
        void onMovieItemClick();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieItemImage;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            movieItemImage = (ImageView) itemView.findViewById(R.id.iv_movie_item);
            itemView.setOnClickListener(this);
        }

        void bind() {
            Picasso.with(itemView.getContext()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(movieItemImage);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onMovieItemClick();
        }
    }



}
