package com.nextbit.yassin.cinemaudacityapi.presenter;

import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;

/**
 * Created by Andy on 01-Jan-15.
 */
public interface OnVersionNameSelectionChangeListener {

    public void OnSelectionChanged(Movie m,int key);
}
