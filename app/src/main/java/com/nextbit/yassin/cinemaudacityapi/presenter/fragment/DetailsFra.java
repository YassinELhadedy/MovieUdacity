package com.nextbit.yassin.cinemaudacityapi.presenter.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Review;
import com.nextbit.yassin.cinemaudacityapi.domain.model.ReviewList;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.CacheImpl;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.MovieDataRepository;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource.MovieDataStoreFactory;
import com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter.ReviewsAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailsFra extends Fragment {
    final static String KEY_POSITION = "position";
    public static  int fragkey=0;
    int mCurrentPosition = -1;

    private ImageView pic;
    private TextView title;
    private TextView count;
    private TextView overvieew;
    private RatingBar star;
    private View view;

    private RecyclerView recyclerView;
    private ReviewsAdapter adapter;
    private List<Review> reviewList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailsFra() {
        // Required empty public constructor
    }


    public static DetailsFra newInstance(String param1, String param2) {
        DetailsFra fragment = new DetailsFra();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_details, container, false);

        pic=(ImageView)view.findViewById(R.id.imageView);
        title=(TextView)view.findViewById(R.id.textView);
        count=(TextView)view.findViewById(R.id.textView2);
        overvieew=(TextView)view.findViewById(R.id.textView3) ;
        star=(RatingBar)view.findViewById(R.id.ratingBar2);
        star.setVisibility(View.INVISIBLE);





        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void load_Review(String id ,String path){
        reviewList=new ArrayList<>();

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_viewreview);
        adapter = new ReviewsAdapter(getActivity(), reviewList,path) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
        load(id);

    }
    public void load(String id){
        fragkey=0;
        MovieDataRepository movieDataRepository=new MovieDataRepository(new MovieDataStoreFactory(getActivity(),new CacheImpl(getActivity())));
        movieDataRepository.movieReviewsStore(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReviewList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ReviewList soAnswersResponse) {
                        adapter.updateMovies(soAnswersResponse.getReviews());
                    }
                });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onStart() {
        super.onStart();

        // During the startup, we check if there are any arguments passed to the fragment.
        // onStart() is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method below
        // that sets the description text
        Bundle args = getArguments();
        Movie movie = null;
        if (args != null){
            // Set description based on argument passed in
          movie =new Movie();
            movie.setPosterPath(args.getString("image"));
            movie.setTitle(args.getString("name"));
            movie.setVoteAverage(args.getDouble("rate"));
            movie.setVoteCount(args.getInt("count"));
            movie.setOverview(args.getString("overview"));
            movie.setId(args.getInt("id"));



            setDescription(movie,args.getInt(KEY_POSITION));
        } else if(mCurrentPosition != -1){


            // Set description based on savedInstanceState defined during onCreateView()
            setDescription(movie,mCurrentPosition);
        }
    }

    public void setDescription(Movie movie,int descriptionIndex){
        DecimalFormat decimalFormat = new DecimalFormat("#");

        float c=movie.getVoteAverage().floatValue()-3;
        star.setVisibility(View.VISIBLE);



        Glide.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(pic);
        title.setText(movie.getTitle());
        count.setText(""+movie.getVoteCount());
        star.setRating((c));
        overvieew.setText(movie.getOverview());
        load_Review(""+movie.getId(),movie.getPosterPath());
        mCurrentPosition = descriptionIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current description selection in case we need to recreate the fragment
        outState.putInt(KEY_POSITION,mCurrentPosition);
    }






}
