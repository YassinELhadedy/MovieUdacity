package com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.nextbit.yassin.cinemaudacityapi.domain.model.Review;

import java.util.List;

/**
 * Created by yassin on 10/10/17.
 */

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Review> reviewList;
    private String path;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView author, content;
        public ImageView pic;


        public MyViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.textView5);
            content = (TextView) view.findViewById(R.id.textView4);
            pic = (ImageView) view.findViewById(R.id.imageView2);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Review item = getItem(getAdapterPosition()); //get item clicked
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
            mContext.startActivity(intent);


            notifyDataSetChanged();

        }
    }


    public ReviewsAdapter(Context mContext, List<Review> reviewList,String path) {
        this.mContext = mContext;
        this.reviewList = reviewList;
        this.path=path;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Review review = reviewList.get(position);




        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185/"+path).into(holder.pic);


        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Review item = getItem(position); //get item clicked



                notifyDataSetChanged();

            }
        });

    }




    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void updateMovies(List<Review> items) {
        reviewList = items;
        notifyDataSetChanged();
    }

    private Review getItem(int adapterPosition) {
        return reviewList.get(adapterPosition);
    }


}
