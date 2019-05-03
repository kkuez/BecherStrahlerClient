package com.example.doerpinghaus.leuchteclient;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


import java.net.Socket;

import static com.example.doerpinghaus.leuchteclient.Logging.loggen;

public class MainActivity extends AppCompatActivity {
    IO io ;
    TikToker tikTok;
    Button connectButton;
    Button resetButton;
    TextView connectStatustextView;
    EditText IPeditText;
    Looper IOLooper;
    Handler IOHandler;
    SeekBar rotSeekBar;
    SeekBar blauSeekBar;
    SeekBar gruenSeekBar;



    int debugPressed=0;
    int startSeekBarValue;


    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        if(connected){
            rotSeekBar.setEnabled(true);
            blauSeekBar.setEnabled(true);
            gruenSeekBar.setEnabled(true);
        }}

    boolean connected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connectButton = (Button)findViewById(R.id.connectButton) ;
        resetButton=(Button)findViewById(R.id.resetButton);
        connectStatustextView = (TextView)findViewById(R.id.connectStatustextView);
        connectStatustextView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                debugPressed++;
                if(debugPressed>5){
                    loggen("======Switched to DebugActivity");
                    switchToDebugActivity();
                    debugPressed=0;
                }
            }
        });

        IPeditText = (EditText)findViewById(R.id.IPeditText);
        rotSeekBar=(SeekBar)findViewById(R.id.rotSeekbar);
        rotSeekBar.setEnabled(false);
        rotSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startSeekBarValue=rotSeekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarSenden(1,seekBar.getProgress());
                loggen( "(Pin 1 gesetzt "+startSeekBarValue+" -> "+rotSeekBar.getProgress()+" )");
            }
        });
        blauSeekBar=(SeekBar)findViewById(R.id.blauSeekBar);
        blauSeekBar.setEnabled(false);
        blauSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startSeekBarValue=blauSeekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarSenden(5,seekBar.getProgress());
                loggen( "(Pin 5 gesetzt "+startSeekBarValue+" -> "+blauSeekBar.getProgress()+" )");
            }
        });
        gruenSeekBar=(SeekBar)findViewById(R.id.gruenSeekBar);
        gruenSeekBar.setEnabled(false);
        gruenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startSeekBarValue=gruenSeekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarSenden(6,seekBar.getProgress());
                loggen( "(Pin 6 gesetzt "+startSeekBarValue+" -> "+gruenSeekBar.getProgress()+" )");
            }
        });

    }

    public void resetSenden(View v){

        IOHandler.post(new Runnable() {
            @Override
            public void run() {
               mkMessageAndSend("reset");
            }
        });
        loggen("======Resettet.");
    }

    public void switchToDebugActivity(){
        Intent myIntent = new Intent(this, DebugActivity.class);
        startActivity(myIntent);
    }

    public void seekBarSenden(final int seekBarNummer, final int aufladeZeit){
        IOHandler.post(new Runnable() {
            @Override
            public void run() {
               mkMessageAndSend(seekBarNummer+";"+aufladeZeit);
            }
        });

    }



    public void mkMessageAndSend(String msg){
        Message neuemsg = Message.obtain();
        neuemsg.obj = msg;
        io.mHandler.handleMessage(neuemsg);
    }

    public void setIOLooper(Looper in){
        IOLooper=in;
        IOHandler=new Handler(IOLooper);
    }
    public void changeConnectStatusTextView(String rein){
        connectStatustextView.setText(rein);
    }

    public void verbinden(View v){
        if(io==null) {
           io=new IO(IPeditText.getText().toString(), this);
           io.start();
        }else{
            if(!isConnected())
                io.verbinden(IPeditText.getText().toString());
        }

    }
    }

