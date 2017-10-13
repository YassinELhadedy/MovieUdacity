/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource;


import com.nextbit.yassin.cinemaudacityapi.domain.model.MoviesList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.ReviewList;
import com.nextbit.yassin.cinemaudacityapi.domain.model.TrailerList;

import rx.Observable;


public interface MovieDataStore {

  Observable<MoviesList> movieListPopularStore(String spec);

  Observable<MoviesList> movieListTopRatedStore(String spec);

  Observable<TrailerList> movieTrailerStore(String movId);

  Observable<ReviewList> movieReviewsStore(String movId);

}
