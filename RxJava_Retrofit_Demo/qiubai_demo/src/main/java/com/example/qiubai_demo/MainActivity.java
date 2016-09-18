package com.example.qiubai_demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements JokeAdapter.JokeAdapterListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    public static String BASEURL = "http://m2.qiushibaike.com/article/list/";
    private int currentPage = 1;
    private JokeService jokeService;
    private List<JokeBean.ItemsBean> list;
    private JokeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData(){
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder().//
                baseUrl(BASEURL).//
                addConverterFactory(GsonConverterFactory.create()).//
                addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
                .build();
        jokeService = retrofit.create(JokeService.class);
        //Rxjava
        list = new ArrayList<>();
        adapter = new JokeAdapter(MainActivity.this, list);
        adapter.setJokeAdapterListener(this);
        recyclerView.setAdapter(adapter);
        loadData(currentPage++);
    }

    private void loadData(int currentPage){
        jokeService.getSuggest(currentPage).//
                subscribeOn(Schedulers.io()).//
                observeOn(AndroidSchedulers.mainThread())//
                .flatMap(new Func1<JokeBean, Observable<List<JokeBean.ItemsBean>>>(){
                    @Override
                    public Observable<List<JokeBean.ItemsBean>> call(JokeBean jokeBean){
                        return Observable.just(jokeBean.getItems());
                    }
                })//
                .subscribe(new Action1<List<JokeBean.ItemsBean>>(){
                    @Override
                    public void call(List<JokeBean.ItemsBean> itemsBeen){
                        if(list != null && list.size() > 0){
                            list.clear();
                            list.addAll(itemsBeen);
                        }
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyler);
        //设置成ListView模式
        LinearLayoutManager llManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(llManager);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                loadData(currentPage++);
            }
        });
    }

    @Override
    public void onJokeItemClicked(int position){
        Toast.makeText(this, position + "位置被点击了", Toast.LENGTH_SHORT).show();
    }
}
