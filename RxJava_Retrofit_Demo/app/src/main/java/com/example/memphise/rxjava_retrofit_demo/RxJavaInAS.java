package com.example.memphise.rxjava_retrofit_demo;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by memphise on 2016/8/2.
 */
public class RxJavaInAS{
   public void main(){
       Observable.create(new Observable.OnSubscribe<String>(){

           @Override
           public void call(Subscriber<? super String> subscriber){
               subscriber.onNext("nihao");

           }
       }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<String>(){
                   @Override
                   public void onCompleted(){
                   }

                   @Override
                   public void onError(Throwable e){
                   }

                   @Override
                   public void onNext(String s){
                       System.out.println(s+"");
                   }
               });
   }
}
