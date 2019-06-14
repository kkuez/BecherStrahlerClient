package com.example.doerpinghaus.leuchteclient;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LooperThread extends Thread {
public Handler loopHandler;
public MainActivity mainActivity;

public LooperThread(MainActivity mainActivity){
    this.mainActivity=mainActivity;

}

    public void run(){
        Looper.prepare();

        loopHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mainActivity.io.senden((String) msg.obj);
            }
        };

        Looper.loop();

    }
}
