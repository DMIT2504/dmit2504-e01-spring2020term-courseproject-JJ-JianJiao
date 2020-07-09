package com.example.jj.mymediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button playButton;
    private SeekBar positionSeekBar;
    private SeekBar volumnSeekBar;
    private TextView elapsedTimeLabel;
    private TextView remainingTimeLabel;
    private MediaPlayer mp;
    private int totalTime;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.media_type_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.play_video_menu_item:
                Intent intent = new Intent(this,VideoMediaPlayer.class);
                mp.pause();
                playButton.setBackgroundResource(R.drawable.play);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.playBtn);
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLable);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLable);

        mp = MediaPlayer.create(this,R.raw.main_theme_overworld);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        totalTime = mp.getDuration();

        positionSeekBar = (SeekBar) findViewById(R.id.positionBar);
        positionSeekBar.setMax(totalTime);
        positionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);
                    positionSeekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumnSeekBar = (SeekBar) findViewById(R.id.volumeBar);
        volumnSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumnNum = progress / 100f;
                mp.setVolume(volumnNum,volumnNum);
//                mp.setVolume(volumnNum,0.5f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Thread (Update posintionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mp != null){
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentPostion = msg.what;
            positionSeekBar.setProgress(currentPostion);

            String elapsedTime = createTimeLabel(currentPostion);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel((totalTime-currentPostion));
            remainingTimeLabel.setText(remainingTime);
        }
    };


    public String createTimeLabel(int time){
        String timeLabel = "";
        int min = time/1000/60;
        int sec = time/1000%60;

        timeLabel = min + ":";
        if(sec < 10){
            timeLabel += "0";
        }
        timeLabel += sec;

        return timeLabel;
    }

    public void playBtnClick(View view) {

        if(!mp.isPlaying()){
            mp.start();
            playButton.setBackgroundResource(R.drawable.stop);
        }
        else{
            mp.pause();
            playButton.setBackgroundResource(R.drawable.play);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
        playButton.setBackgroundResource(R.drawable.play);
    }

    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }
}