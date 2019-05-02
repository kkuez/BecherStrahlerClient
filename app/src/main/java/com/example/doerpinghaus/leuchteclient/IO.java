package com.example.doerpinghaus.leuchteclient;
import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class IO extends Thread {
    public IO(MainActivity main){

        mainActivity = main;
    }


    MainActivity mainActivity;
    Socket socket;
    Handler handlerForMain= new Handler(Looper.getMainLooper());    //https://medium.com/@yossisegev/understanding-activity-runonuithread-e102d388fe93

    @Override
    public void run() {

        if(socket==null){
            {
                try {
                    //socket = new Socket("10.0.75.1", 55551);
                    socket = new Socket("192.168.0.2", 55551);
                    handlerForMain.post(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.changeConnectStatusTextView("Verbunden.");
                            mainActivity.setIOLooper(Looper.myLooper());
                        }
                    });
                    System.out.println("jo");
                    senden("asd");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
        while(true){}
    }

    public void senden(String raus){
        if(socket!=null){
            PrintWriter printWriter;
            try {
                printWriter   = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.print(raus);
                printWriter.flush();
              //  printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
