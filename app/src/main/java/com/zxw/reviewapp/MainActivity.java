package com.zxw.reviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxw.reviewapp.ctrip.Constant;
import com.zxw.reviewapp.ctrip.MyModel;
import com.zxw.reviewapp.ctrip.RecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MyModel> source = new ArrayList<>();
    RecyclerviewAdapter recyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newData();

        recyclerView = findViewById(R.id.my_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        recyclerviewAdapter = new RecyclerviewAdapter(this, source);
        recyclerView.setAdapter(recyclerviewAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void newData(){

        MyModel myModel = new MyModel();
        myModel.index = 0;
        myModel.name = "我是第0条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 55;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 1;
        myModel.name = "我是第1条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 34;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 2;
        myModel.name = "我是第2条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 12;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 3;
        myModel.name = "我是第3条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 5;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 4;
        myModel.name = "我是第4条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 2;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 5;
        myModel.name = "我是第5条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 44;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 6;
        myModel.name = "我是第6条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 32;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 7;
        myModel.name = "我是第7条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 19;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 8;
        myModel.name = "我是第8条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 4;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 9;
        myModel.name = "我是第9条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 60;
        source.add(myModel);

        myModel = new MyModel();
        myModel.index = 10;
        myModel.name = "我是第10条数据";
        myModel.status = Constant.SPEECH_STATUS_DEFAULT;
        myModel.time = 45;
        source.add(myModel);

    }
}
