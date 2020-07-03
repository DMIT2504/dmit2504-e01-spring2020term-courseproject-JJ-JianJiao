package com.example.jj.framelayoutdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PhotoView extends View {

    public float bitmapX;
    public float bitmapY;

    public PhotoView(Context context) {
        super(context);
        init();
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        bitmapX = 0;
        bitmapY = 200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.meimei);

        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);

        if(bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
