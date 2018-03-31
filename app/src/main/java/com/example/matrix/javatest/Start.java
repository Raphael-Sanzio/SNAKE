package com.example.matrix.javatest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.matrix.javatest.SnakeView.direction_up;

public class Start extends Activity {
    private static final String tag = "SnakeActivity";
    SnakeView view;
    Button up,down,left,right;
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new SnakeView(this);
        setContentView(view);
        if (view.isGameOver()){
            new AlertDialog.Builder(Start.this)
                    .setTitle("游戏结束！")
                    .setMessage("您的得分为"+view.snakeNum)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Start.this,MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        view.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                if (SnakeView.direction!=SnakeView.direction_down) SnakeView.direction= direction_up;
            } else if(y2 - y1 > 50) {
                if (SnakeView.direction!= direction_up) SnakeView.direction=SnakeView.direction_down;
            } else if(x1 - x2 > 50) {
                if (SnakeView.direction!=SnakeView.direction_right) SnakeView.direction=SnakeView.direction_left;
            } else if(x2 - x1 > 50) {
                if (SnakeView.direction!=SnakeView.direction_left) SnakeView.direction=SnakeView.direction_right;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(SnakeView.direction!= direction_up)
                    SnakeView.direction=SnakeView.direction_down;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(SnakeView.direction!=SnakeView.direction_down)
                    SnakeView.direction=SnakeView.direction_up;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(SnakeView.direction!=SnakeView.direction_right)
                    SnakeView.direction=SnakeView.direction_left;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(SnakeView.direction!=SnakeView.direction_left)
                    SnakeView.direction=SnakeView.direction_right;
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, Menu.FIRST+2, 0, "继续").setShortcut('3', 'c');//设置快捷键
        menu.add(0, Menu.FIRST, 0, "重新开始").setShortcut('1', 'p');//设置快捷键
        menu.add(0, Menu.FIRST+1, 0, "关于...").setShortcut('2', 'a');//设置快捷键
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        view.isPaused=true;//显示菜单时暂停游戏
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // TODO Auto-generated method stub
        view.isPaused=false;//关闭菜单时继续游戏
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                if(view!=null){
                    view.setFocusable(true);
                    view.init();
                }
                break;
            case Menu.FIRST+1:
                Dialog aboutDialog=new AlertDialog.Builder(this)
                        .setTitle("贪吃蛇")
                        .setMessage("贪吃蛇V1.0")
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("访问首页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.cancel();
                                Uri uri=Uri.parse("http://www.iteye.com");
                                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(intent);
                            }
                        })
                        .create();
                aboutDialog.setCanceledOnTouchOutside(true);
                aboutDialog.show();
                break;
            case Menu.FIRST+2:
                view.isPaused=false;
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}