package com.example.doerpinghaus.leuchteclient;

import android.support.v4.util.TimeUtils;
import android.text.format.DateUtils;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Logging {
    static StringBuilder logg = new StringBuilder();

    public static void loggen(String rein){
        String time = String.valueOf(Calendar.getInstance().getTime());
        if(time.contains("GM")) {

        }
        if (time.contains("ME")){
            logg.append(time.substring(0, time.indexOf("M")) + rein + "\n");

        }
        System.out.println(rein);
    }
    public static String getLog(){
        return logg.toString();
    }
    public static void loggen(Exception e){
        for (StackTraceElement ste :e.getStackTrace()){
            logg.append(ste.toString()+"\n");
        }
        e.printStackTrace();
    }
}
