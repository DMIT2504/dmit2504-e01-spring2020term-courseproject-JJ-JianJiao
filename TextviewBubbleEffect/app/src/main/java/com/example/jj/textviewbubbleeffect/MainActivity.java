package com.example.jj.textviewbubbleeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private BubbleTextview bubbleTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleTextview = findViewById(R.id.bubbleTextView);

        bubbleTextview.setmPointText("99");
    }
}