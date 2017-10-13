package com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.CacheImpl;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.EntityCache;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.MovieDataRepository;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource.MovieDataStoreFactory;
import com.nextbit.yassin.cinemaudacityapi.presenter.activity.twomodeActivity.FavActivity;

import java.util.List;


/**
 * Created by yassin on 10/11/17.
 */

public class FavAdapter  extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;
    private PostItemListener mItemListener;
    private static int number;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        private PostItemListener mItemListener;

        public MyViewHolder(View view,PostItemListener postItemListener) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie item = getItem(getAdapterPosition()); //get item clicked

            this.mItemListener.onPostClick(item,0);

            notifyDataSetChanged();

        }
    }


    public FavAdapter(Context mContext, List<Movie> movieList,PostItemListener itemListener) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.mItemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView,this.mItemListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.count.setText(movie.getReleaseDate() + " Released");

        // loading album cover using Glide library
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
                number=position;


            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie item = getItem(position); //get item clicked

                mItemListener.onPostClick(item,0);

                notifyDataSetChanged();

            }
        });

    }





    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateMovies(List<Movie> items) {
        movieList = items;
        notifyDataSetChanged();
    }

    private Movie getItem(int adapterPosition) {
        return movieList.get(adapterPosition);
    }
    public interface PostItemListener {
        void onPostClick(Movie movie,int key);
    }//interface to transport item id to activity
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {

        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_fav, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();

    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:


                    Toast.makeText(mContext, "remove from favourite", Toast.LENGTH_SHORT).show();
                    Movie item2 = getItem(number); //get item clicked
                    movieList.remove(item2);
                    MovieDataRepository movieDataRepository=new MovieDataRepository(new MovieDataStoreFactory(mContext, new CacheImpl(mContext)
                    ));
                    movieDataRepository.deleteFavMovie(item2);
                    notifyDataSetChanged();
                    notifyItemRemoved(number);
                    if (isTablet(mContext)){
                        mContext.startActivity(new Intent(mContext, FavActivity.class));
                    }
                    return true;


                default:
            }
            return false;
        }
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}