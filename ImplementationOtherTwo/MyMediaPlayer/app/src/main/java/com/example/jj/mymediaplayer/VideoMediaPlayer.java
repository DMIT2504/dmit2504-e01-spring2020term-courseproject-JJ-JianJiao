package com.example.jj.mymediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VideoMediaPlayer extends AppCompatActivity implements
        View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private MediaPlayer mMediaPlayer = null;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Button videoStartBtn;
    private Button fullScreenBtn;
    private LinearLayout videoLinearLayout;
//    private Button btn_stop;

    private SeekBar positionSeekBar;
    private SeekBar volumnSeekBar;
    private TextView elapsedTimeLabel;
    private TextView remainingTimeLabel;
    private int totalTime;


//    private Handler handler = new Handler();
//    private Runnable runnable = new Runnable() {
//        public void run() {
//            if (mMediaPlayer.isPlaying()) {
//                int current = mMediaPlayer.getCurrentPosition();
//                positionSeekBar.setProgress(current);
//                elapsedTimeLabel.setText(time(mMediaPlayer.getCurrentPosition()));
//            }
//            handler.postDelayed(runnable, 500);
//        }
//    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_media_player);
        bindViews();



        positionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mMediaPlayer.seekTo(progress);
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

        volumnSeekBar = findViewById(R.id.videoVolumeBar);
        volumnSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumnNum = progress / 100f;
                mMediaPlayer.setVolume(volumnNum,volumnNum);
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
                while(mMediaPlayer != null){
                    try {
                        Message videoMsg = Message.obtain();
                        videoMsg.what = mMediaPlayer.getCurrentPosition();
                        videoHandler.sendMessage(videoMsg);
                        Thread.sleep(1000);
                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();
    }


    private Handler videoHandler = new Handler(){
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


//    protected String time(long millionSeconds) {
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(millionSeconds);
//        return simpleDateFormat.format(c.getTime());
//    }

    private void bindViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.videoSurfaceView);
//        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);
        videoStartBtn = (Button) findViewById(R.id.videoPlayBtn);

        videoLinearLayout = findViewById(R.id.videoLinearLayout);

        fullScreenBtn = findViewById(R.id.fullScreenPlayBtn);
//        btn_stop = (Button) findViewById(R.id.videoStopBtn);

        videoStartBtn.setOnClickListener(this);
        fullScreenBtn.setOnClickListener(this);

        elapsedTimeLabel = (TextView)findViewById(R.id.videoElapsedTimeLable);
        remainingTimeLabel = (TextView)findViewById(R.id.videoRemainingTimeLable);

        positionSeekBar = findViewById(R.id.videoPositionBar);
//        btn_stop.setOnClickListener(this);

        //initial SurfaceHolder Class，SurfaceView
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFixedSize(960, 540);   //setting Resolution
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer = MediaPlayer.create(this, R.raw.do_not_tell_her);
        //
        mMediaPlayer.setLooping(false);
        mMediaPlayer.seekTo(0);
        mMediaPlayer.setVolume(0.5f,0.5f);


        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        totalTime = mMediaPlayer.getDuration();
        positionSeekBar.setMax(totalTime);
        changeVideoSize();

        mMediaPlayer.setDisplay(mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        changeVideoSize();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoPlayBtn:
                if(!mMediaPlayer.isPlaying()){
                    mMediaPlayer.start();
                    videoStartBtn.setBackgroundResource(R.drawable.stop);
                }else{
                    mMediaPlayer.pause();
                    videoStartBtn.setBackgroundResource(R.drawable.play);
                }
                break;
            case R.id.fullScreenPlayBtn:
                Toast.makeText(getApplicationContext(), "fullScreen", Toast.LENGTH_SHORT).show();
                if (Configuration.ORIENTATION_LANDSCAPE == this.getResources()
                        .getConfiguration().orientation) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                changeVideoSize();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
    }

    public void changeVideoSize() {

        int surfaceWidth = mSurfaceView.getLayoutParams().width;
        int surfaceHeight= mSurfaceView.getLayoutParams().height;

        int videoWidth = mMediaPlayer.getVideoWidth();
        int videoHeight = mMediaPlayer.getVideoHeight();


        float max;
        if (getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {

            max = Math.max((float) videoWidth / (float) surfaceWidth,(float) videoHeight / (float) surfaceHeight);
            videoLinearLayout.setPadding(0,60,0,0);
        } else{

            max = Math.max(((float) videoWidth/(float) surfaceHeight),(float) videoHeight/(float) surfaceWidth);
            max = 2;
            videoLinearLayout.setPadding(0,0,0,0);
        }


        videoWidth = (int) Math.ceil((float) videoWidth / max);
        videoHeight = (int) Math.ceil((float) videoHeight / max);


        mSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));

//        int surfaceWidth = mSurfaceView.getLayoutParams().width;
//        int surfaceHeight= mSurfaceView.getLayoutParams().height;
//        mSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(surfaceWidth, surfaceHeight));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}