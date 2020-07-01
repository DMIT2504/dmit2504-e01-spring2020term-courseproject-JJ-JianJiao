package com.example.jj.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static MainActivity mainActivity = null;



    private GameView mGameView;
    private TextView dispPlayScoreTextView;
    private TextView lableScoreTextView;
    private TextView dispPlayBestTextView;
    private TextView lableBestTextView;
    private LinearLayout scoreLinearLayout;
    private LinearLayout bestLinearLayout;

    private int score = 0;

    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mGameView = findViewById(R.id.gameMainView);
        dispPlayScoreTextView = findViewById(R.id.displaySocre_textview);
        lableScoreTextView = findViewById(R.id.lable_score_textview);
        dispPlayBestTextView = findViewById(R.id.lable_best_textview);
        lableBestTextView = findViewById(R.id.displayBest_textview);
        scoreLinearLayout = findViewById(R.id.score_linear_layout);
        bestLinearLayout = findViewById(R.id.best_linear_layout);

        /*Setting property of layout: have 3 ways.
        1.use java to set
        2.set in xml file
        3. create a background xml file in drawable folder. and set background
         */
        scoreLinearLayout.setPadding(20,20,20,20);
//        scoreLinearLayout.setBackgroundColor(Color.parseColor("#bbada0"));
//        bestLinearLayout.setPadding(20,20,20,20);
//        bestLinearLayout.setBackgroundColor(Color.parseColor("#bbada0"));

//        lableScoreTextView.setBackgroundColor(Color.parseColor("#faf8ef"));
//        dispPlayScoreTextView.setBackgroundColor(Color.parseColor("#faf8ef"));
        lableScoreTextView.setTextColor(Color.WHITE);
        dispPlayScoreTextView.setTextColor(Color.WHITE);
        lableBestTextView.setTextColor(Color.WHITE);
        dispPlayBestTextView.setTextColor(Color.WHITE);
    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void showScore(){
        dispPlayScoreTextView.setText(score+"");
    }

    public void addScore(int earnScore){
        score += earnScore;
        showScore();
    }

    public void Reset(View view) {
//        mGameView//
    }
}