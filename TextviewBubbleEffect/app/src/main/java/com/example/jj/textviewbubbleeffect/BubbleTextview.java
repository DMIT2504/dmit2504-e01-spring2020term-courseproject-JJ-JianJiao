package com.example.jj.textviewbubbleeffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BubbleTextview extends androidx.appcompat.widget.AppCompatTextView {

    private int mWidth;
    private int mHeight;
    private float _density = 1;
    private Paint mPiant = null;
    private int textColor = Color.RED;

    private float textSize = 8f;
    private int strokeWidth = 2;

    private float mPointX = 0;
    private float mPointY = 0;
    private float mPointTextWidth = 0;
    private float mPointTextHeight = 0;
    private CharSequence mPointText = null;
    private float circleY = 0;
    private float circleX = 0;
    private Paint circlePaint = null;

    private int circleColor = Color.RED;

    private int circlestrokeWidth = 2;

    private float offsetX = 0;

    private float offsetY = 0;

    private float maxWidth = 0;

    public BubbleTextview(Context context) {
        super(context);
        init(context);
    }

    public BubbleTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public BubbleTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        _density = context.getResources().getDisplayMetrics().density;
        mPiant = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiant.setColor(textColor);
        mPiant.setStrokeWidth(strokeWidth);
        mPiant.setTextSize(textSize * _density);
        mPiant.setStyle(Paint.Style.FILL);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(circlestrokeWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (this.getText() != null) {
            if (mPointText!=null&&!mPointText.equals("")) {

                maxWidth = mPiant.measureText("99+");

                mPointTextWidth = mPiant.measureText(String.valueOf(mPointText));


                mPointX = mWidth-maxWidth/2-mPointTextWidth/2-offsetX;
                Paint.FontMetrics metrics = mPiant.getFontMetrics();

                float textH = maxWidth+offsetY;

                mPointTextHeight = (textH-(metrics.descent-metrics.ascent))/2-metrics.ascent;

                mPointY = mPointTextHeight+1*_density;

                circleX = mWidth-(maxWidth)/2-offsetX;

                circleY = textH/2+1*_density;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPointText!=null&&!mPointText.equals("")) {
            canvas.drawCircle(circleX, circleY, maxWidth/2+1*_density, circlePaint);
            if (Integer.parseInt(mPointText.toString())>99) {
                canvas.drawText("99+", mPointX, mPointY, mPiant);
            }else
                canvas.drawText(mPointText.toString(), mPointX, mPointY, mPiant);
        }
    }

    public void setmPointText(CharSequence mPointText) {
        this.mPointText = mPointText;
        invalidate();
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
}
