package com.zxw.reviewapp.ctrip;

/**
 * Created by xiangwuzhu on 2018/11/25.
 */

public class MyModel {

    public int index;
    public String name;
    public int time;
    public int status;
    public int remainTime;


    @Override
    public String toString() {
        return "MyModel{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", status=" + status +
                ", remainTime=" + remainTime +
                '}';
    }
}
