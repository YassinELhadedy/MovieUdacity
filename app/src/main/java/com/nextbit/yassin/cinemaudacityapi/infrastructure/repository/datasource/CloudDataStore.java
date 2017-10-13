package com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource;


import com.nextbit.yassin.cinemaudacityapi.domain.model.MoviesList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.ReviewList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.TrailerList;
import com.nextbit.yassin.cinemaudacityapi.domain.service.SOService;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.EntityCache;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.service.ApiUtils;

import rx.Observable;

/**
 * Created by yassin on 10/8/17.
 */

public class CloudDataStore implements MovieDataStore {
    private final EntityCache entityCache;
    private SOService mService;

    public CloudDataStore(EntityCache entityCache) {
        this.entityCache = entityCache;
        mService= ApiUtils.getSOService();
    }


    @Override
    public Observable<MoviesList> movieListPopularStore(String spec) {
        return this.mService.getMoviesListPopular(spec).doOnNext(entityCache::putMovies);
    }

    @Override
    public Observable<MoviesList> movieListTopRatedStore(String spec) {

        return this.mService.getMoviesListTopRated(spec).doOnNext(entityCache::putMovies);

    }

    @Override
    public Observable<TrailerList> movieTrailerStore(String movId) {
        return this.mService.getTrailares(movId).doOnNext(entityCache::putTrailers);

    }

    @Override
    public Observable<ReviewList> movieReviewsStore(String movId) {
        return this.mService.getReviews(movId).doOnNext(entityCache::putReviews);
    }
}
