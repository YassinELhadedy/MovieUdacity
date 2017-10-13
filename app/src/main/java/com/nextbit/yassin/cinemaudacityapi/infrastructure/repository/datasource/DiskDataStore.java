package com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource;


import com.nextbit.yassin.cinemaudacityapi.domain.model.MoviesList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.ReviewList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.TrailerList;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.EntityCache;

import rx.Observable;

/**
 * Created by yassin on 10/8/17.
 */

public class DiskDataStore implements MovieDataStore {
    private final EntityCache entityCache;

    public DiskDataStore(EntityCache entityCache) {
        this.entityCache = entityCache;
    }


    @Override
    public Observable<MoviesList> movieListPopularStore(String spec) {
        return entityCache.getMoviesCache();
    }

    @Override
    public Observable<MoviesList> movieListTopRatedStore(String spec) {
        return entityCache.getMoviesCache();
    }

    @Override
    public Observable<TrailerList> movieTrailerStore(String movId) {
        return entityCache.getTrailersCache(movId);
    }

    @Override
    public Observable<ReviewList> movieReviewsStore(String movId) {
        return entityCache.getReviewsCache(movId);
    }
}
