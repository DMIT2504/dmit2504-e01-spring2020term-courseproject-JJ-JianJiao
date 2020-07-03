package com.example.jj.framelayoutdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class FrameLayoutDemo02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_demo02);

        FrameLayout frameLayout = findViewById(R.id.FrameLayout02);
        final PhotoView  photoView = new PhotoView(this);

        photoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                photoView.bitmapX = event.getX() - 150;
                photoView.bitmapY = event.getY() - 150;

                photoView.invalidate();
                return true;
            }
        });

        frameLayout.addView(photoView);
    }
}