package com.example.memphise.rxjava_retrofit_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        listView = (ListView) findViewById(R.id.listivew);
        textView = (TextView) findViewById(R.id.tv);
    }

    public void show(View view){
        String baseUrl = "https://api.douban.com/v2/movie/";
        //创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).//
                addConverterFactory(GsonConverterFactory.create()).//
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).//
                build();
        News_Iterface news_iterface = retrofit.create(News_Iterface.class);
        //Rxjava:
        news_iterface.getTopMovie(0,10)
                .subscribeOn(Schedulers.io())//
        .observeOn(AndroidSchedulers.mainThread())//
        .flatMap(new Func1<MovieBean, Observable<List<MovieBean.SubjectsBean>>>(){

            @Override
            public Observable<List<MovieBean.SubjectsBean>> call(MovieBean movieBean){
                return Observable.just(movieBean.getSubjects());
            }
        }).subscribe(new Subscriber<List<MovieBean.SubjectsBean>>(){
            @Override
            public void onCompleted(){
            }

            @Override
            public void onError(Throwable e){
            }

            @Override
            public void onNext(List<MovieBean.SubjectsBean> list){
                MovieAdapter adapter = new MovieAdapter(MainActivity.this,list);
                listView.setAdapter(adapter);
            }
        });
    }
}
