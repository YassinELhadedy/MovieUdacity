package com.nextbit.yassin.cinemaudacityapi.presenter.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Trailer;
import com.nextbit.yassin.cinemaudacityapi.domain.model.TrailerList;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.CacheImpl;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.MovieDataRepository;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource.MovieDataStoreFactory;
import com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter.TrailerAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TrailerFra extends Fragment {

    private RecyclerView recyclerView;
    private TrailerAdapter adapter;
    private List<Trailer> trailerList;
    private View view;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrailerFra() {
        // Required empty public constructor
    }


    public static TrailerFra newInstance(String param1, String param2) {
        TrailerFra fragment = new TrailerFra();
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
        view=inflater.inflate(R.layout.fragment_trailer, container, false);
        Bundle getsbunle=getArguments();


        load_Trailer(""+getsbunle.getInt("id"),getsbunle.getString("poster"),getsbunle.getString("name"));
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
//
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

    public void load_Trailer(String id ,String path,String name){
        trailerList=new ArrayList<>();

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_trailerfra);
        adapter = new TrailerAdapter(getActivity(), trailerList,name,path) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
        load(id);

    }
    public void load(String id){
        MovieDataRepository movieDataRepository=new MovieDataRepository(new MovieDataStoreFactory(getActivity(),new CacheImpl(getActivity())));
        movieDataRepository.movieTrailerStore(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TrailerList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(TrailerList soAnswersResponse) {
                        adapter.updateMovies(soAnswersResponse.getTraliers());
                    }
                });
    }
}
