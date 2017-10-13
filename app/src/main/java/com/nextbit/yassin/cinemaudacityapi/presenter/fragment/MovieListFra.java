package com.nextbit.yassin.cinemaudacityapi.presenter.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;
import com.nextbit.yassin.cinemaudacityapi.domain.model.MoviesList;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.CacheImpl;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.MovieDataRepository;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource.MovieDataStoreFactory;
import com.nextbit.yassin.cinemaudacityapi.presenter.OnVersionNameSelectionChangeListener;
import com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter.GridSpacingItemDecoration;
import com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter.MoviesAdapter;


import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieListFra extends Fragment {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    private View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MovieListFra() {
        // Required empty public constructor
    }


    public static MovieListFra newInstance(String param1, String param2) {
        MovieListFra fragment = new MovieListFra();
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
        view= inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_viewfra);
        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(getActivity(), movieList, new MoviesAdapter.PostItemListener() {
            @Override
            public void onPostClick(Movie movie,int key) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                OnVersionNameSelectionChangeListener listener = (OnVersionNameSelectionChangeListener) getActivity();
                listener.OnSelectionChanged(movie,key);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        load();

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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    public void load(){
        DetailsFra.fragkey=1;

        MovieDataRepository movieDataRepository=new MovieDataRepository(new MovieDataStoreFactory(getActivity(),new CacheImpl(getActivity())));
        movieDataRepository.movieListTopRatedStore(getPerference())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoviesList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(MoviesList soAnswersResponse) {
                        adapter.updateMovies(soAnswersResponse.getmovies());
                    }
                });
    }
    public String getPerference(){
        String listPrefs;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listPrefs = prefs.getString("listpref", "popular");
        Log.i("this sgared ",listPrefs);
        return listPrefs;


    }
}
