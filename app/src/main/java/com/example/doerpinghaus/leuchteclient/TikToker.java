package com.example.doerpinghaus.leuchteclient;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.doerpinghaus.leuchteclient.Logging.loggen;

public class TikToker extends Thread {
    String ip;
    Socket socket;
    MainActivity mainActivity;
    List<TikTok> tiktokListe = new ArrayList<>();
    TikTok tiktokZumFuellen=new TikTok(true, Calendar.getInstance().getTimeInMillis()) ;
    Message msg;
IO io;

    public TikToker(String ip, MainActivity mainActivityrein, Socket socket, IO io) {
        this.io=io;
         msg= Message.obtain();
        msg.obj="tik";
        this.mainActivity=mainActivityrein;
        this.ip = ip;
        this.socket=socket;
    }

    @Override
    public void run(){
        tiktokListe.add(tiktokZumFuellen);
        tiktokListe.add(tiktokZumFuellen);


        while(true){
            try {

                mainActivity.io.looperThread.loopHandler.handleMessage(msg);
                Thread.sleep(5000);
                if((Calendar.getInstance().getTimeInMillis()-tiktokListe.get(0).zeitPunktInMillis)>5000||tiktokListe.get(0).gueltig==false){
                    setTiktokListe(new TikTok(false,Calendar.getInstance().getTimeInMillis()));

                }
                if(tiktokListe.size()>3){
                    tiktokListe.remove(3);
                }
                if(!tiktokListe.get(0).gueltig&&!tiktokListe.get(1).gueltig&&!tiktokListe.get(2).gueltig){

                    io.handlerForMain.post(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.setConnected(false);
                        }
                    });
                    System.out.println("Verbindung verloren.");
                    break;
                }
            } catch (InterruptedException e) {
                loggen(e);
                break;
            }


        }

    }
    public void setTiktokListe(TikTok rein){
        tiktokListe.add(0,rein);
    }

}
