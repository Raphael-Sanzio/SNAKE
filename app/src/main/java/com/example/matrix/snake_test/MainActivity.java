package com.example.matrix.snake_test;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button button_start=(Button)findViewById(R.id.button_begin);
        Button button_rule=(Button)findViewById(R.id.button_rule);
        button_start.setOnClickListener(onClick);

    }

    public View.OnClickListener onClick=new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.button_rule:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("游戏规则")
                            .setMessage("通过操纵小蛇去吃水果，蛇身越长，你的分数越高")
                            .setPositiveButton("确定",null)
                            .show();
                    break;
                case R.id.button_begin:
                    Intent intent=new Intent(MainActivity.this,Game.class);
                    startActivity(intent);break;
            }

        }
    };
}
