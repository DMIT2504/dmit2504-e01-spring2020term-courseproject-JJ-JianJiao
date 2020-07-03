package com.example.jj.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static MainActivity mainActivity = null;



    public GameView mGameView;
    private TextView dispPlayScoreTextView;
    private TextView lableScoreTextView;
    private TextView dispPlayBestTextView;
    private TextView lableBestTextView;
    private LinearLayout scoreLinearLayout;
    private LinearLayout bestLinearLayout;
    public Button newGameButton;
    private TextView game2048NameTextview;
    private int score = 0;
    private int bestScore = 0;

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
        game2048NameTextview = findViewById(R.id.game2048_name_title_textview);


        dispPlayScoreTextView = findViewById(R.id.displaySocre_textview);
        lableScoreTextView = findViewById(R.id.lable_score_textview);
        dispPlayBestTextView = findViewById(R.id.displayBest_textview);
        lableBestTextView = findViewById(R.id.lable_best_textview);
        scoreLinearLayout = findViewById(R.id.score_linear_layout);
        bestLinearLayout = findViewById(R.id.best_linear_layout);
        newGameButton = findViewById(R.id.start_new_game_button);
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        bestScore = sharedPreferences.getInt("best", 0);


        showScore();
        showBestScore();

//        Paint mPaint1 = new Paint();
//        mPaint1.setColor(Color.parseColor("red"));
//        Canvas canvas = new Canvas();
//        canvas.drawRect(100, 100, 150, 150, mPaint1);

        setGame2048TitleStateListAnimator();
        //initNewButtonOnTouchAnim();
        game2048NameTextview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(game2048NameTextview,"textColor",Color.parseColor("#776e65"),
                        Color.parseColor("#2196f3"),Color.parseColor("#ffea00"), Color.parseColor("#776e65"));
                objectAnimator.setDuration(5000);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.start();
                return false;
            }
        });
    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void showScore(){
        dispPlayScoreTextView.setText(score+"");
    }

    public void showBestScore(){
        dispPlayBestTextView.setText(bestScore+"");
    }

    public void addScore(int earnScore){
        score += earnScore;
        if(score>=bestScore){
            bestScore = score;
            showBestScore();
        }
        showScore();
    }

    public void Reset() {
        mGameView.resetGame();
        clearScore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        bestScore = sharedPreferences.getInt("best", 0);
        showBestScore();

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("best", bestScore);
        //editor.putBoolean("switch_on_off", false);
        editor.commit();
    }

    public void startNewGameOnClick(View view) {
        new AlertDialog.Builder(view.getContext())
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Reset();
                        newGameButton.setBackgroundResource(R.drawable.new_game_button_background);

                        mGameView.setAlpha(0f);
                        mGameView.setRotation(0f);
                        mGameView.setVisibility(View.VISIBLE);
                        mGameView.animate().alpha(1f).rotation(360).setDuration(3000).setListener(null);

                    }
                })
                .setNegativeButton(R.string.continue_game, null)
                .setTitle(R.string.reset_dialog_title)
                .setMessage(R.string.reset_dialog_message)
                .show();
    }

    /* state-animation
     * ref:https://developer.android.com/reference/android/animation/StateListAnimator
     * the difference between an animator and an animation:
     * https://stackoverflow.com/questions/28220613/what-is-the-difference-between-an-animator-and-an-animation
     */
    private void setGame2048TitleStateListAnimator(){
        StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(this,R.animator.game2048_game_title_state_change);
        game2048NameTextview.setStateListAnimator(stateListAnimator);
    }
    private void initNewButtonOnTouchAnim() {
        int[] attrs = new int[]{R.drawable.new_game_button_background};
        TypedArray typedArray = obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        newGameButton.setBackgroundResource(backgroundResource);
    }
}