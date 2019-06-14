package com.example.doerpinghaus.leuchteclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ScrollView logScrollView = (ScrollView) findViewById(R.id.logScrollView);
        TextView logtextView = (TextView) findViewById(R.id.logtextView);
        logtextView.setText(Logging.getLog());

    }
}
