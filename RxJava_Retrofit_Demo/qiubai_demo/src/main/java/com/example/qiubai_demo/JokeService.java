package com.example.qiubai_demo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by memphise on 2016/8/2.
 */
public interface JokeService{
    @GET("suggest")
    Observable<JokeBean> getSuggest(@Query("page")int page);
}
