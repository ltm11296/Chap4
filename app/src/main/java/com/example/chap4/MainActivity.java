package com.example.chap4;

import android.app.Activity;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {
    private int seconds = 0;
    private boolean running;
    private boolean wasrunning;


    @Override
    protected void onCreate(Bundle st) {
        super.onCreate(st);
        setContentView(R.layout.activity_main);

        if (st != null) {
            seconds = st.getInt("seconds");
            running = st.getBoolean("running");
            wasrunning =st.getBoolean("wasrunning",wasrunning);
        }
        runTimer();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasrunning){
            running=true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning =running;
        running = false;
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.tv);
        final Handler handle = new Handler();
        handle.post(new Runnable() {
            @Override
            public void run() {
                int hour = seconds / 3600;
                int minute = seconds / 60;
                int sec = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hour, minute, sec);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handle.postDelayed(this, 1000);
            }

        });

    }

    protected void onSaveInstanceState(Bundle st) {

        st.putInt("seconds", seconds);
        st.putBoolean("running", running);
        st.putBoolean("wasrunning", wasrunning);
    }

}
