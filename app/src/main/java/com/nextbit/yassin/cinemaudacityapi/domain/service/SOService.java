package com.nextbit.yassin.cinemaudacityapi.domain.service;


import com.nextbit.yassin.cinemaudacityapi.domain.model.MoviesList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.ReviewList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.TrailerList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Elhadedy on 4/9/2017.
 */


public interface SOService {


    @GET("{param}?api_key=d84728f2cfb003c8298b599386b39e34")
    Observable<MoviesList> getMoviesListPopular(@Path("param") String movspec);

    @GET("{param}?api_key=d84728f2cfb003c8298b599386b39e34")
    Observable<MoviesList> getMoviesListTopRated(@Path("param") String movspec);

    @GET("{param}/videos?api_key=d84728f2cfb003c8298b599386b39e34")
    Observable<TrailerList> getTrailares(@Path("param") String movId);

    @GET("{param}/reviews?api_key=d84728f2cfb003c8298b599386b39e34")
    Observable<ReviewList> getReviews(@Path("param") String movId);
}