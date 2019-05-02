package com.example.doerpinghaus.leuchteclient;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    IO io ;
    Button connectButton;
    TextView connectStatustextView;
    EditText IPeditText;
    Looper IOLooper;
    Handler IOHandler;
    SeekBar rotSeekBar;
    SeekBar blauSeekBar;
    SeekBar gruenSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       connectButton = (Button)findViewById(R.id.connectButton) ;
        connectStatustextView = (TextView)findViewById(R.id.connectStatustextView);
        IPeditText = (EditText)findViewById(R.id.IPeditText);
        rotSeekBar=(SeekBar)findViewById(R.id.rotSeekbar);
        rotSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarSenden(1,seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blauSeekBar=(SeekBar)findViewById(R.id.blauSeekBar);
        blauSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarSenden(5,seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        gruenSeekBar=(SeekBar)findViewById(R.id.gruenSeekBar);
        gruenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarSenden(6,seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }





    public void seekBarSenden(final int seekBarNummer, final int aufladeZeit){
        IOHandler.post(new Runnable() {
            @Override
            public void run() {
                io.senden(seekBarNummer+";"+aufladeZeit);
            }
        });

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
            io = new IO(this);
            io.start();

        }
    }
}
