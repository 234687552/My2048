package com.example.administrator.my2048;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GameActivity extends Activity {
    GameView gameView;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        gameView = (GameView) findViewById(R.id.game_view);
        score = (TextView) findViewById(R.id.score);



        //监听touch事件
        gameView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = startX - event.getX();
                        offsetY = startY - event.getY();
                        if (Math.sqrt(offsetX * offsetX + offsetY * offsetY) < 3) {
                            break;
                        }
                        int jiaodu=Math.round((float) (Math.asin(offsetY / Math.sqrt(offsetX * offsetX + offsetY * offsetY)) / Math.PI * 180));
                        //Log.w("角度",jiaodu+"");
                        if (Math.abs(jiaodu)<=15){
                            if (offsetX<0){
                                //右
                                //Log.w("onTouch", "右 ");
                                gameView.swipeRight();
                                score.setText(String.valueOf(gameView.getScore()));
                            }else {
                                //左
                                //Log.w("onTouch", "左 " );
                                gameView.swipeLeft();
                                score.setText(String.valueOf(gameView.getScore()));
                            }
                        }else if (Math.abs(jiaodu)>=75){
                            if (offsetY<0){
                                //下
                                //Log.w("onTouch", "下 ");
                                gameView.swipeDown();
                                score.setText(String.valueOf(gameView.getScore()));
                            }else {
                                //上
                                //Log.w("onTouch", "上 " );
                                gameView.swipeUp();
                                score.setText(String.valueOf(gameView.getScore()));
                            }
                        }else if (jiaodu>=0){
                            if (offsetX<0){
                                //右上
                                ////Log.w("onTouch", "右上 " );
                                gameView.ObliqueRight();
                                score.setText(String.valueOf(gameView.getScore()));
                            }else {
                                //左上
                                gameView.ObliqueLeft();
                                score.setText(String.valueOf(gameView.getScore()));
                                //Log.w("onTouch", "左上 " );
                            }
                        }else if (jiaodu<0){
                            if (offsetX<0){
                                //右下
                                //Log.w("onTouch", "右下 " );
                                gameView.inclinedRight();
                                score.setText(String.valueOf(gameView.getScore()));
                            }else {
                                //左下
                                //Log.w("onTouch", "左下 ");
                                gameView.inclinedLeft();
                                score.setText(String.valueOf(gameView.getScore()));
                            }
                        }





//                        if (offsetY > 45) {
//                            if (offsetX < -45) {
//                                //右上
//                                gameView.ObliqueRight();
//                                score.setText(String.valueOf(gameView.getScore()));
//                            } else if (offsetX > 45) {
//                                //左上
//                                gameView.ObliqueLeft();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//                            } else {
//                                //向上
//                                gameView.swipeUp();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//                            }
//                        } else if (offsetY < -45) {
//                            if (offsetX < -45) {
//                                //右下
//                                gameView.inclinedRight();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//                            } else if (offsetX > 45) {
//                                //左下
//                                gameView.inclinedLeft();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//
//                            } else {
//                                //向下
//                                gameView.swipeDown();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//
//                            }
//                        } else {
//                            if (offsetX < 0) {
//                                //向右
//                                gameView.swipeRight();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//
//                            } else {
//                                //向左
//                                gameView.swipeLeft();
//                                score.setText(String.valueOf(gameView.getScore()));
//
//                            }
//                        }

                        break;
                }
                return true;
            }
        });
    }


    public void newGame(View view) {
        gameView.NewGame();
        score.setText(String.valueOf(gameView.getScore()));
    }

}
