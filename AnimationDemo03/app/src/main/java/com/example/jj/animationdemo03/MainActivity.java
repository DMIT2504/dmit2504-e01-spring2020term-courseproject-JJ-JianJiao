package com.example.jj.animationdemo03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tapToFade(View view) {
        Button button = findViewById(R.id.button5);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);
        button.startAnimation(animation);
    }

    public void toBlink(View view) {
        Button button = findViewById(R.id.button);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.blink);
        button.startAnimation(animation);
    }

    public void toSlide(View view) {
        Button button = findViewById(R.id.button3);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide);
        button.startAnimation(animation);
    }

    public void toMove(View view) {
        Button button = findViewById(R.id.button4);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.move);
        button.startAnimation(animation);
    }
}