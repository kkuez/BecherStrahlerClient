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
import java.util.Calendar;
import java.util.List;

import static com.example.doerpinghaus.leuchteclient.Logging.loggen;

public class IO extends Thread {
    ;
    String ip;
    MainActivity mainActivity;
    Socket socket;
    public Handler mHandler;
    TikToker tikTok;
    boolean inputHandler;
    LooperThread looperThread;
    public IO(String ip, MainActivity mainActivityrein){
        mainActivity=mainActivityrein;
        this.ip =ip;
        mHandler=new Handler();
    }




    Handler handlerForMain= new Handler(Looper.getMainLooper());


    void verbinden(String ip){
        try {


            if(ip.equals("IP")){
                socket=new Socket("10.0.75.1", 55551);

            }else{
                socket=new Socket(ip, 55551);
            }

            handlerForMain.post(new Runnable() {
                @Override
                public void run() {
                    mainActivity.setConnected(true);
                    mainActivity.setIOLooper(Looper.myLooper());
                }
            });


            tikTok = new TikToker(ip,mainActivity, socket, this);
            tikTok.start();
        }catch (Exception e){
            e.printStackTrace();
            loggen(e);
        }



        if(socket!=null) {



        }
    }

    String leseNachricht() throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        char[] buffer = new char[200];
        int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
        String nachricht="";
        if(anzahlZeichen>1) {
            nachricht = new String(buffer, 0, anzahlZeichen);
        }
        return nachricht;
    }

    public void prepare(){



    }
    @Override
    public void run() {
        looperThread = new LooperThread(mainActivity);
        looperThread.start();



        if (tikTok == null) {
            verbinden(ip);
        }
        while(true){
            if (!mainActivity.connected && tikTok != null) {
                handlerForMain.post(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.connectButton.setEnabled(true);
                        mainActivity.connectStatustextView.setText("Verbindung verloren.");
                    }
                });

            }

            String nachricht = "";
            try {
                while(mainActivity.connected) {
                    handlerForMain.post(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.changeConnectStatusTextView("Verbunden.");
                            mainActivity.connectButton.setEnabled(false);
                        }
                    });
                    nachricht = leseNachricht();
                    if (nachricht.equals("tok")) {
                        tikTok.setTiktokListe(new TikTok(true, Calendar.getInstance().getTimeInMillis()));
                        System.out.println("tok");
                    }
                }
            } catch (IOException e) {
                loggen(e);
            }}
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


   */




    void senden(String raus){

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
