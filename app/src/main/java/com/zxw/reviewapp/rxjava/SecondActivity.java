package com.zxw.reviewapp.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xiangwuzhu on 2018/11/24.
 */

public class SecondActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//
//            }
//        }).subscribeOn(new Scheduler() {
//            @Override
//            public Worker createWorker() {
//                return null;
//            }
//        });


//        Observable.fromArray("").flatMap("", new Observable<>());

    }
}
