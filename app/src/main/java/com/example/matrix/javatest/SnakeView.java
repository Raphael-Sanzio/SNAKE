package com.example.matrix.javatest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;


    public class SnakeView extends View implements Runnable{

        private static final String tag="SnakeView";
        public boolean isPaused=false;
        private int[][] snake=new int[10000][2];//100节点总数，第二维下标0代表x坐标，1代表y坐标
        public int snakeNum;
        public static int direction;
        public final static int direction_up=1;
        public final static int direction_down=2;
        public final static int direction_left=3;
        public final static int direction_right=4;
        private int width=1050,height=1600;
        private final int SNAKEWIDTH=25;
        private int SLEEP_TIME;
        private int foodX,foodY;
        private Random r;
        private Thread t;
        private Paint paint;

        public SnakeView(Context context) {
            super(context);
            r=new Random();
            paint=new Paint();
            init();
            t=new Thread(this);
            t.start();
        }
        public SnakeView(Context context, AttributeSet attrs){
            super(context,attrs);
            inial(context,attrs);
        }
        public void inial(Context context,AttributeSet attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.SnakeView_view);
            int textColor = typedArray.getColor(R.styleable.SnakeView_view_text_color,0xFFFFFF);
            int backgroundcolor=typedArray.getColor(R.styleable.SnakeView_view_text_back_ground,0xFFFFFF);
        }

        public void init(){
            snakeNum=6;
            for(int i=0;i<snakeNum;i++){
                snake[i][0]=500-SNAKEWIDTH*i;
                snake[i][1]=500;
            }
            direction=direction_right;
            generateFood();
            SLEEP_TIME=200;
            isPaused=false;
        }
        @Override
        public void run() {
            while(true){
                if(!isPaused){
                    if(isGameOver()){
                        isPaused=true;
                    }else{
                        eatFood();
                        move(direction);
                    }
                }
                postInvalidate();
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isPaused) break;
            }

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            canvas.drawLine(0,0,width,0,paint);
            canvas.drawLine(0,0,0,height,paint);
            canvas.drawLine(0,height,width,height,paint);
            canvas.drawLine(width,0,width,height,paint);
            canvas.drawRect(0,0,width,SNAKEWIDTH,paint);
            canvas.drawRect(0,0,SNAKEWIDTH,height,paint);
            canvas.drawRect(0,height,width+SNAKEWIDTH,height+SNAKEWIDTH,paint);
            canvas.drawRect(width,0,width+SNAKEWIDTH,height,paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("你的得分为"+(snakeNum-6),0,height+4*SNAKEWIDTH,paint);
            paint.setColor(Color.BLUE);
            for(int i=0;i<snakeNum;i++){
                canvas.drawRect(snake[i][0], snake[i][1], snake[i][0]+SNAKEWIDTH, snake[i][1]+SNAKEWIDTH, paint);
            }
            paint.setColor(Color.RED);
            canvas.drawRect(foodX,foodY,foodX+SNAKEWIDTH,foodY+SNAKEWIDTH,paint);
        }


        private void eatFood(){
            if(snake[0][0]==foodX&&snake[0][1]==foodY){
                snakeNum++;
                generateFood();
            }
        }

        private void generateFood(){
            while(true){
                foodX=Math.abs(r.nextInt()%(width-SNAKEWIDTH+1))/SNAKEWIDTH*SNAKEWIDTH;
                foodY=Math.abs(r.nextInt()%(height-SNAKEWIDTH+1))/SNAKEWIDTH*SNAKEWIDTH;
                boolean b=true;
                for(int i=0;i<snakeNum;i++){
                    if(foodX==snake[i][0]&&foodY==snake[i][1]){
                        b=false;
                        break;
                    }
                }
                if(b){
                    break;
                }
            }
        }

        private void move(int direction){
            for(int i=snakeNum-1;i>0;i--){
                snake[i][0]=snake[i-1][0];
                snake[i][1]=snake[i-1][1];
            }
            switch (direction) {
                case direction_up:
                    snake[0][1]=snake[0][1]-SNAKEWIDTH;
                    break;
                case direction_down:
                    snake[0][1]=snake[0][1]+SNAKEWIDTH;
                    break;
                case direction_left:
                    snake[0][0]=snake[0][0]-SNAKEWIDTH;
                    break;
                case direction_right:
                    snake[0][0]=snake[0][0]+SNAKEWIDTH;
                    break;
                default:
                    break;
            }
        }

        public  boolean isGameOver(){
            if((snake[0][0]<SNAKEWIDTH)||(snake[0][0]>width-SNAKEWIDTH)||(snake[0][1]<SNAKEWIDTH)||(snake[0][1]>height-SNAKEWIDTH)){
                return true;
            }
            for(int i=SNAKEWIDTH-1;i<snakeNum;i++){
                if(snake[0][0]==snake[i][0]&&snake[0][1]==snake[i][1]){
                    return true;
                }
            }
            return false;
        }

    }
