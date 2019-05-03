package com.example.doerpinghaus.leuchteclient;
import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.example.doerpinghaus.leuchteclient.Logging;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static com.example.doerpinghaus.leuchteclient.Logging.loggen;

public class IO extends Thread {


           static MainActivity mainActivity;
           static Socket socket;
            public Handler mHandler;



    String ip;



    @Override
    public void run() {
        Looper.prepare();

         mHandler = new Handler() {
            public void handleMessage(Message msg) {


            }
        };

        Looper.loop();
    }


   /* @Override
    public void run() {


                handlerForMain.post(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.changeConnectStatusTextView("Verbunden.");
                        mainActivity.setConnected(true);
                        mainActivity.setIOLooper(Looper.myLooper());
                    }
                });
                loggen("======Client angemeldet.");
                senden("000;000");


   */ }




    public void senden(String raus){

            PrintWriter printWriter;
            try {
                printWriter   = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.print(raus);
                printWriter.flush();
                //  printWriter.close();
            } catch (IOException e) {
                loggen(e);
            }
        }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
