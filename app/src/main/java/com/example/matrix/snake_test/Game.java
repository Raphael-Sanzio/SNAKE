package com.example.matrix.snake_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        LinearLayout layout=(LinearLayout)findViewById(R.id.root);
        TextView textView=new TextView(this);
        textView.setText("你的得分为："+SnakeView.snakeNum);
        layout.addView(textView);
        SnakeView snakeView=new SnakeView(this);
        snakeView.setMinimumHeight(480);
        snakeView.setMinimumWidth(320);
        layout.addView(snakeView);
        Button button_up,button_down,button_left,button_right;
        button_up=(Button)findViewById(R.id.id_up);
        button_down=(Button)findViewById(R.id.id_down);
        button_left=(Button)findViewById(R.id.id_left);
        button_right=(Button)findViewById(R.id.id_right);
        button_up.setOnClickListener(onClick);
        button_down.setOnClickListener(onClick);
        button_left.setOnClickListener(onClick);
        button_right.setOnClickListener(onClick);
    }

    public View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.id_up:
                    if (SnakeView.direction!=SnakeView.direction_down){
                        SnakeView.direction=SnakeView.direction_up;
                    }
                    break;
                case R.id.id_down:
                    if (SnakeView.direction!=SnakeView.direction_up){
                        SnakeView.direction=SnakeView.direction_down;
                    }
                    break;
                case R.id.id_left:
                    if (SnakeView.direction!=SnakeView.direction_right){
                        SnakeView.direction=SnakeView.direction_left;
                    }
                    break;
                case R.id.id_right:
                    if (SnakeView.direction!=SnakeView.direction_left){
                        SnakeView.direction=SnakeView.direction_right;
                    }
                    break;
            }
        }
    };
}
