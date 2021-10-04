package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int seconds=0;
    private boolean running;
    private boolean wasRunning;

    Button start,stop,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");

        }
        runTimer();

        start=findViewById(R.id.start);
        start.setOnClickListener(this);
        stop=findViewById(R.id.stop);
        stop.setOnClickListener(this);
        reset=findViewById(R.id.reset);
        reset.setOnClickListener(this);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning",wasRunning);
    }

    private void runTimer() {

        final Handler handler=new Handler();
        TextView tvTime=findViewById(R.id.tvTime);

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int mins=(seconds%3600)/60;
                int secs=seconds%60;

                String time=String.format(Locale.getDefault(),"%d:%02d:%02d",hours,mins,secs);
                tvTime.setText(time);
                if (running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                running=true;
                break;
            case R.id.stop:
                running=false;
                break;
            case R.id.reset:
                running=false;
                seconds=0;
                break;


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning){
            running=true;
        }
    }
}
