package com.example.matrix.snake_test;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

/**
 * Created by Matrix on 2018/3/30.
 */

public class SnakeView extends View implements Runnable{
    public boolean isPaused = false;
    public boolean isRunning = true;
    public static int direction;
    public static final int direction_up = 1;
    public static final int direction_down = 2;
    public static final int direction_left = 3;
    public static final int direction_right = 4;
    private int[][] snake = new int[100][2];
    public static int snakeNum;
    private int snakewidth = 5;
    private int foodx, foody;
    public Paint paint;
    private int width = 320, height = 480;
    private Random r;
    private long SLEEP_TIME=200;
    Thread thread;


    public SnakeView(Context context){
        super(context);
        paint = new Paint();
        init();

    }
    public void init() {
        snakeNum = 12;
        for (int i = 0; i < snakeNum; i++) {
            snake[i][0] = 100 - snakewidth * i;
            snake[i][1] = 100;
        }
        direction = direction_right;
        generetefood();
        thread=new Thread(this);
        thread.start();
        isRunning = true;
        isPaused = false;
    }

    @Override
    public void run(){
        while(isRunning){
            if (!isPaused){
                if (!isGameover()){
                move(direction);
                eatfood();
                    try {
                        thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    isPaused=true;
                }
            }
            else{
                isRunning=false;
                break;
            }
            postInvalidate();
        }
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(0xffffff);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i <snakeNum ; i++) {
            canvas.drawRect(snake[i][0],snake[i][1],snake[i][0]+snakewidth,snake[i][1]+snakewidth,paint);
        }
        paint.setColor(Color.WHITE);
        canvas.drawRect(foodx,foody,foodx+snakewidth,foody+snakewidth,paint);

    }
    private void generetefood(){
        while (true){
            foodx=Math.abs(r.nextInt()%(width-snakeNum+1))/snakewidth*snakewidth;
            foodx=Math.abs(r.nextInt()%(height-snakeNum+1))/snakewidth*snakewidth;
            boolean b=true;
            for (int i = 0; i < snakeNum; i++) {
                if (foodx==snake[i][0]&&foody==snake[i][1]){
                    b=false;
                }
                if (b){
                    break;
                }
            }
        }
    }
    private void eatfood(){
        if (snake[0][0]==foodx&&snake[0][1]==foody){
            snakeNum++;
            generetefood();
        }
    }
    private void move(int direction){
        for (int i = snakeNum-1 ;i>0; i--) {
            snake[i][0]=snake[i-1][0];
            snake[i][1]=snake[i-1][1];
        }
        switch (direction){
            case direction_up:snake[0][1]=snake[0][1]-snakewidth;break;
            case direction_down:snake[0][1]=snake[0][1]+snakewidth;break;
            case direction_left:snake[0][0]=snake[0][0]-snakewidth;break;
            case direction_right:snake[0][0]=snake[0][0]+snakewidth;break;
            default:break;
        }
    }
    private boolean isGameover(){
        if (snake[0][0]<0||snake[0][0]>width-snakewidth||snake[0][1]<0||snake[0][1]>height-snakewidth){
            return true;
        }
        for (int i = 4; i <snakeNum ; i++) {
            if ((snake[0][0]==snake[i][0])&&(snake[0][1]==snake[i][1])){
                return true;
            }
        }
        return false;
    }


}
