package com.example.jj.framelayoutdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

public class FrameLayoutDemo03 extends AppCompatActivity {

    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_demo03);

        frameLayout = (FrameLayout) findViewById(R.id.FrameLayout03);


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                handler.sendEmptyMessage(0x222);
            }
        }, 0,170);
    }

    //connect with different thread
    //ref : https://developer.android.com/reference/android/os/Handler
    Handler handler = new Handler(){
        int i = 0;

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0x222){
                i++;
                move(i%8);
            }
            super.handleMessage(msg);
        }
    };

    private void move(int i) {
        Drawable a = getResources().getDrawable(R.drawable.m_1);
        Drawable b = getResources().getDrawable(R.drawable.m_2);
        Drawable c = getResources().getDrawable(R.drawable.m_3);
        Drawable d = getResources().getDrawable(R.drawable.m_4);
        Drawable e = getResources().getDrawable(R.drawable.m_5);
        Drawable f = getResources().getDrawable(R.drawable.m_6);
        Drawable g = getResources().getDrawable(R.drawable.m_7);
        Drawable h = getResources().getDrawable(R.drawable.m_8);

        switch(i)
        {
            case 0:
                frameLayout.setForeground(a);
                break;
            case 1:
                frameLayout.setForeground(b);
                break;
            case 2:
                frameLayout.setForeground(c);
                break;
            case 3:
                frameLayout.setForeground(d);
                break;
            case 4:
                frameLayout.setForeground(e);
                break;
            case 5:
                frameLayout.setForeground(f);
                break;
            case 6:
                frameLayout.setForeground(g);
                break;
            case 7:
                frameLayout.setForeground(h);
                break;
        }
    }
}