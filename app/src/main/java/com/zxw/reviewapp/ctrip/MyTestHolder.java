package com.zxw.reviewapp.ctrip;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zxw.reviewapp.R;

/**
 * Created by xiangwuzhu on 2018/11/25.
 */

public class MyTestHolder extends RecyclerView.ViewHolder {
    private final int PLAY_INTERVAL = 1;
    private final int SPEECH_STATUS_DEFAULT = 0;
    private final int SPEECH_STATUS_PLAYING = 1;
    private final int SPEECH_STATUS_PAUSE = 2;
    private final int SPEECH_STATUS_END = 3;

    private final int HANDLER_SPEECH_DEFAULT = 10;
    private final int HANDLER_SPEECH_PLAYING = 11;
    private final int HANDLER_SPEECH_PAUSE = 12;
    private final int HANDLER_SPEECH_END = 13;

    private Context mContext;
    private MyModel myModel;

    public TextView name;
    public TextView time;
    public TextView status;
    public Button play;

    private MsgTimeThread timeThread;
    private MsgHandler handler;

    private int speechStatus = SPEECH_STATUS_DEFAULT;
    private int remainMs = 0;

    public MyTestHolder(View itemView, Context context) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        time = itemView.findViewById(R.id.time);
        status = itemView.findViewById(R.id.status);
        play = itemView.findViewById(R.id.play);
        this.mContext = context;

    }


    public void setData(final MyModel model, int position){
        myModel = model;
        Log.e("zxw", "初始化 myModel="+myModel.toString());
        name.setText(model.name);
        status.setText(getStatusString(model.status));

        if (model.status != SPEECH_STATUS_DEFAULT){
            remainMs = model.remainTime;
        }else {
            remainMs = model.time;
        }
        time.setText(getPrefixTime(remainMs));


        handler = new MsgHandler();
        Log.e("zxw", "初始化 handler="+handler);
        speechStatus = model.status;


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zxw", "点击了Play按钮");
                invokeHandler(speechStatus);
            }
        });


        if(speechStatus!= SPEECH_STATUS_DEFAULT){
            Log.e("zxw", name.getText()+" 数据恢复");
            switch (speechStatus) {
                case SPEECH_STATUS_PLAYING:

                    handler.sendEmptyMessage(HANDLER_SPEECH_PLAYING);
                    break;

                case SPEECH_STATUS_PAUSE:
                    handler.sendEmptyMessage(HANDLER_SPEECH_PAUSE);
                    break;

                case SPEECH_STATUS_END:
                    Log.e("zxw", "当前状态 停止，不可能点击到");
                    break;

                default:
                    resetAudioStatus();
            }
        }

    }


    private void invokeHandler(int status){
        String str = "当前状态是：" + status + " " + getStatusString(status);
        switch (status) {
            case SPEECH_STATUS_PLAYING:
                str =str+" 转 暂停";
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                Log.e("zxw", str);

                handler.sendEmptyMessage(HANDLER_SPEECH_PAUSE);
                break;

            case SPEECH_STATUS_PAUSE:
                str =str+" 转 播放";
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                Log.e("zxw", str);

                handler.sendEmptyMessage(HANDLER_SPEECH_PLAYING);

                break;

            case SPEECH_STATUS_END:
                Log.e("zxw", "当前状态 停止，不可能点击到");
                break;

            default:
                Log.e("zxw", "默认 转 播放");
                str =str+" 转 播放";
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                Log.e("zxw", str);

                handler.sendEmptyMessage(HANDLER_SPEECH_PLAYING);
        }
    }

    public void resetAudioStatus(){
        speechStatus = SPEECH_STATUS_DEFAULT;
        status.setText(getStatusString(speechStatus));
        myModel.status = speechStatus;
        time.setText("" + myModel.time);
        remainMs = myModel.time;
        myModel.remainTime = remainMs;
    }


    private void startTimeThread(Handler uihandler, int time) {
//        if (timeThread!=null && timeThread.isAlive()) return;

        timeThread = new MsgTimeThread();
        timeThread.initThread(uihandler, time);
        timeThread.start();

        Log.e("zxw", "创建的timeThread="+timeThread);
    }

    class MsgHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == HANDLER_SPEECH_PLAYING) {
                //这里有问题，恢复的状态是播放中，没起Thread，case错了
                if (speechStatus != SPEECH_STATUS_PLAYING) {
                    Log.e("zxw", "handleMessage 更新 暂停/初始化 转播放 --");

                    //播放
                    speechStatus = SPEECH_STATUS_PLAYING;




                    //之前这里没立即更新数据源,记录一下
//                    myModel.status = SPEECH_STATUS_PLAYING;




                    status.setText(getStatusString(speechStatus));

                    //
                    startTimeThread(handler, remainMs);

                } else if (timeThread ==null || timeThread.isInterrupted()) {
                    Log.e("zxw", "handleMessage - 数据恢复无Thread ");
                    startTimeThread(handler, remainMs);

                } else{
                    Log.e("zxw", "handleMessage - 播放中 - 当前timeThread=" + timeThread);
                    remainMs = remainMs - PLAY_INTERVAL;

                    //之前这里没立即更新数据源,记录一下
//                    myModel.status = SPEECH_STATUS_PLAYING;
                    //                    myModel.remainTime = remainMs




                    if (remainMs >= 0) {
                        time.setText(getPrefixTime(remainMs));
                    } else {
                        Log.e("zxw", "handleMessage 出错啦， remainMs =" + remainMs);
                    }
                }

            } else if (msg.what == HANDLER_SPEECH_PAUSE) {

                Log.e("zxw", "handleMessage 开始暂停 -timeThread="+timeThread);
                //暂停
                if (timeThread != null && timeThread.isAlive()){
                    Log.e("zxw", "handleMessage 暂停执行了 -- ");
//                    timeThread.interrupt();
                }
                speechStatus = SPEECH_STATUS_PAUSE;



                //之前这里没立即更新数据源,记录一下
//                myModel.status = SPEECH_STATUS_PAUSE;
                //暂停时更新
                myModel.remainTime = remainMs;



                status.setText(getStatusString(speechStatus));



            } else if (msg.what == HANDLER_SPEECH_END) {
                Log.e("zxw", "handleMessage  播放结束 --");

                if (speechStatus != SPEECH_STATUS_END) {
                    speechStatus = SPEECH_STATUS_END;
                    resetAudioStatus();
                }
            }
        }
    }


    class MsgTimeThread extends Thread {

        public Handler uiHandler;
        public int countTime = 0;

        public void initThread(final Handler handler, int time) {
            this.uiHandler = handler;
            this.countTime = time;
        }

        @Override
        public void run() {
            Log.e("zxw", "MsgTimeThread 开始 uiHandler="+uiHandler);

            try {
                while (speechStatus == SPEECH_STATUS_PLAYING && uiHandler != null) {

                    Log.e("zxw", "TimeThread 循环speechStatus=" + speechStatus + " countTime=" + countTime);

                    if (countTime <= 0) {
                        countTime = 0;
                        uiHandler.sendEmptyMessage(HANDLER_SPEECH_END);
                        break;
                    } else if (countTime - PLAY_INTERVAL <= 0) {
                        Thread.sleep(countTime * 1000);
                        countTime = 0;
                        if (speechStatus == SPEECH_STATUS_PLAYING)
                            uiHandler.sendEmptyMessage(HANDLER_SPEECH_END);
                        break;
                    } else {
                        Thread.sleep(PLAY_INTERVAL * 1000);
                        countTime = countTime - PLAY_INTERVAL;
                        if (speechStatus == SPEECH_STATUS_PLAYING)
                            uiHandler.sendEmptyMessage(HANDLER_SPEECH_PLAYING);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    protected void onViewRecycled() {
        myModel.status = speechStatus;
        myModel.remainTime = remainMs;
        if (timeThread!=null && timeThread.isAlive()){
            timeThread.interrupt();
            timeThread = null;
        }
        Log.e("zxw", "--- --- --- onViewRecycled:" + name.getText());

    }







    public void onViewDetachedFromWindow() {
        Log.e("zxw", "--- --- --- onViewDetachedFromWindow" + name.getText());
    }

    public void onViewAttachedToWindow() {
    }

    private String getStatusString(int status) {
        switch (status) {
            case SPEECH_STATUS_PLAYING:
                return "播放";
            case SPEECH_STATUS_PAUSE:
                return "暂停";
            case SPEECH_STATUS_END:
                return "停止";
            default:
                return "默认";
        }
    }

    private String getPrefixTime(int time) {
        return "0:" + (time > 9 ? time : "0" + time);
    }

}
