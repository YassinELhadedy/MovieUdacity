package com.nextbit.yassin.cinemaudacityapi.infrastructure.service;


import com.nextbit.yassin.cinemaudacityapi.domain.service.RetrofitClient;
import com.nextbit.yassin.cinemaudacityapi.domain.service.SOService;

/**
 * Created by Elhadedy on 4/9/2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}