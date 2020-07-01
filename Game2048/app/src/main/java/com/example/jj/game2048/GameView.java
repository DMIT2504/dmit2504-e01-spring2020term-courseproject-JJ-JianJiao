package com.example.jj.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    private static final String TAG = "GameView";
    public static int cardWidth;

    private Context context;

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        this.context = context;
        initialGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initialGameView();
    }

    private void initialGameView(){

        setColumnCount(4);
//        setBackgroundColor(0xffbbada0);
//        setBackgroundColor(Color.parseColor("#bbada0"));
        setOnTouchListener(new View.OnTouchListener(){

            private float startX;
            private float startY;
            private float offSetX;
            private float offSetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offSetX = event.getX()-startX;
                        offSetY = event.getY() - startY;
                        if(Math.abs(offSetX) > Math.abs(offSetY)){
                            if(offSetX<-5){
                                swipeLeft();
                                Log.i(TAG,"Left");
                            }
                            else if(offSetX>5){
                                swipeRight();
                                Log.i(TAG,"Right");
                            }
                        }else{
                            if(offSetY<-5){
                                swipeUp();
                                Log.i(TAG,"UP");
                            }
                            else if(offSetY>5){
                                swipeDown();
                                Log.i(TAG,"DOWN");
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cardWidth = (Math.min(w,h) - 30) / 4;
        addCards(cardWidth,cardWidth);
        startGame();
    }

    private void addCards(int width, int height){
        Card card;

        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col++){
                card = new Card(getContext());
                card.setNum(0);
//                card.setBackgroundResource(R.drawable.game_board_layout);
                addView(card,width,height);
                cardsMap[row][col] = card;
            }
        }
    }

    private void startGame(){

        //MainActivity.getMainActivity().clearScore();

        for(int i = 0; i < 4; i++ ){
            for(int y = 0; y < 4; y++){
                cardsMap[i][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
//        addRandomNum();
//        addRandomNum();
//        addRandomNum();
//        addRandomNum();
//        addRandomNum();
//        addRandomNum();
    }

    private void addRandomNum(){

        emptyPoints.clear();

        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                if(cardsMap[x][y].getNum() <=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point point = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[point.x][point.y].setNum(Math.random()>0.2?2:4);
    }

    private void swipeLeft(){

        boolean merge = false;

        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col++){
                Log.i(TAG,"CardsMap[" + row + "][" + col + "] = " + cardsMap[row][col].getNum());
            }

        }

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int backCol = col+1; backCol< 4; backCol++) {
                    if(cardsMap[row][backCol].getNum()>0){
                        if(cardsMap[row][col].getNum()<=0){

//                            Animation animation = AnimationUtils.loadAnimation(context,R.anim.move);
//                            cardsMap[row][backCol].startAnimation(animation);
//                            int[] oldLocation = new int[2];
//                            int[] newLocation = new int[2];
//                            cardsMap[row][backCol].disPlayNumberTV.getLocationInWindow(oldLocation);
//                            cardsMap[row][col].disPlayNumberTV.getLocationInWindow(newLocation);
//                            cardsMap[row][backCol].animationMove(oldLocation,newLocation);

                            cardsMap[row][col].setNum(cardsMap[row][backCol].getNum());
                            cardsMap[row][backCol].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);
                            col--;
                            merge = true;
                        }
                        else if(cardsMap[row][backCol].equals(cardsMap[row][col])){
                            cardsMap[row][col].setNum(cardsMap[row][col].getNum()*2);
                            cardsMap[row][backCol].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            MainActivity.getMainActivity().addScore(cardsMap[row][col].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge){
            addRandomNum();
            isFinished();
        }
    }

    private void swipeRight(){

        boolean merge = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 3; col >= 0; col--) {
                for (int forwardCol = col-1; forwardCol >= 0; forwardCol--) {
                    if(cardsMap[row][forwardCol].getNum()>0){

                        if(cardsMap[row][col].getNum()<=0){
                            cardsMap[row][col].setNum(cardsMap[row][forwardCol].getNum());
                            cardsMap[row][forwardCol].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            col++;
                            merge = true;
                        }
                        else if(cardsMap[row][forwardCol].equals(cardsMap[row][col])){
                            cardsMap[row][col].setNum(cardsMap[row][col].getNum()*2);
                            cardsMap[row][forwardCol].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            MainActivity.getMainActivity().addScore(cardsMap[row][col].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            isFinished();
        }
    }

    private void swipeUp(){
        boolean merge = false;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                for (int backRow = row+1; backRow< 4; backRow++) {
                    if(cardsMap[backRow][col].getNum()>0){

                        if(cardsMap[row][col].getNum()<=0){
                            cardsMap[row][col].setNum(cardsMap[backRow][col].getNum());
                            cardsMap[backRow][col].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            row--;
                            merge = true;
                        }
                        else if(cardsMap[backRow][col].equals(cardsMap[row][col])){
                            cardsMap[row][col].setNum(cardsMap[row][col].getNum()*2);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            cardsMap[backRow][col].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[row][col].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            isFinished();
        }
    }

    private void swipeDown(){
        boolean merge = false;
        for (int col = 0; col < 4; col++) {
            for (int row = 3; row >= 0; row--) {
                for (int forwardRow = row-1; forwardRow >=0; forwardRow--) {
                    if(cardsMap[forwardRow][col].getNum()>0){

                        if(cardsMap[row][col].getNum()<=0){
                            cardsMap[row][col].setNum(cardsMap[forwardRow][col].getNum());
                            cardsMap[forwardRow][col].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            row++;
                            merge = true;
                        }
                        else if(cardsMap[forwardRow][col].equals(cardsMap[row][col])){
                            cardsMap[row][col].setNum(cardsMap[row][col].getNum()*2);
                            cardsMap[forwardRow][col].setNum(0);

//                            zoomUpAnimation(cardsMap[row][col]);
                            cellAnimationFactory(cardsMap[row][col]);

                            MainActivity.getMainActivity().addScore(cardsMap[row][col].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            isFinished();
        }
    }

    private void isFinished(){

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if(cardsMap[x][y].getNum()==0 ||
                    (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                    (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                    (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                    (y<3&&cardsMap[x][y].equals((cardsMap[x][y+1])))) {
                    complete = false;
                    break ALL;
                }
            }
        }
        if(complete){
            new AlertDialog.Builder(getContext()).setTitle("Hello").setMessage("Game Finish").setPositiveButton("Restart",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }

    private void zoomUpAnimation(Card card){
        AnimationSet animationSet = new AnimationSet(true);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.15f,1.0f,1.15f,
//                Animation.RELATIVE_TO_SELF,0.5f,
//                Animation.RELATIVE_TO_SELF,0.5f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.15f,0.8f,1.15f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(500);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(false);
        card.disPlayNumberTV.startAnimation(animationSet);
    }


    private void rotateAnimation(Card card){
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setInterpolator(context, android.R.anim.anticipate_interpolator);
        card.disPlayNumberTV.startAnimation(animation);
    }

    private void cellAnimationFactory(Card card){
        int number = Integer.valueOf(card.getNum());
        switch(number){
            case 4:
                zoomUpAnimation(card);
                break;
            case 8:
                rotateAnimation(card);
                break;
            case 16:
                break;
            case 32:
                break;
            case 64:
                break;
            case 128:
                break;
            case 256:
                break;
            case 512:
                break;
            case 1024:
                break;
            case 2048:
                break;
            case 4096:
                break;
        }
//        zoomUpAnimation(card);
//        rotateAnimation(card);
    }
}
