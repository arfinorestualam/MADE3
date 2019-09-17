package com.example.apkfin3rev.api;

import com.example.apkfin3rev.BuildConfig;

public class ApiUtils {
    public static final String API_KEY = BuildConfig.ApiKey;
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL = BuildConfig.BaseUrl;

    public static ApiInterface getApi() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
