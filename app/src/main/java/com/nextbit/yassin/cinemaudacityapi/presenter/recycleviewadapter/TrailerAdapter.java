package com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Review;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Trailer;

import java.util.List;

/**
 * Created by yassin on 10/10/17.
 */

public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Trailer> trailerList;
    private String name_movie,path;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, source;
        public ImageView pic;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textView5);
             source= (TextView) view.findViewById(R.id.textView4);
            pic = (ImageView) view.findViewById(R.id.imageView2);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Trailer item = getItem(getAdapterPosition()); //get item clicked
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+item.getKey()));
            mContext.startActivity(intent);



            notifyDataSetChanged();

        }
    }


    public TrailerAdapter(Context mContext, List<Trailer> trailerList,String name_movie,String path) {
        this.mContext = mContext;
        this.trailerList = trailerList;
        this.name_movie=name_movie;
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
        Trailer trailer = trailerList.get(position);




        holder.name.setText(name_movie);
        holder.source.setText(trailer.getSite());
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185/"+path).into(holder.pic);


        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trailer item = getItem(position); //get item clicked



                notifyDataSetChanged();

            }
        });

    }




    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void updateMovies(List<Trailer> items) {
        trailerList = items;
        notifyDataSetChanged();
    }

    private Trailer getItem(int adapterPosition) {
        return trailerList.get(adapterPosition);
    }


}
