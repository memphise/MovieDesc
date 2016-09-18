package com.example.memphise.rxjava_retrofit_demo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by memphise on 2016/8/2.
 */
public interface News_Iterface{

    @GET("top250")
    Observable<MovieBean> getTopMovie(@Query("start") int start, @Query("count") int count);

}
