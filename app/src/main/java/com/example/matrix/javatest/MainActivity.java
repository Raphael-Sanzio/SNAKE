package com.example.matrix.javatest;

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
        setContentView(R.layout.activity_start);
        Button start=(Button)findViewById(R.id.button_start);
        Button rule=(Button)findViewById(R.id.button_rule);
        start.setOnClickListener(onClick);
        rule.setOnClickListener(onClick);
    }

    public View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_start:
                    Intent intent=new Intent(MainActivity.this,Start.class);
                    startActivity(intent);
                    break;
                case R.id.button_rule:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("游戏规则")
                            .setPositiveButton("确定",null)
                            .setMessage("通过操纵蛇来吃水果，蛇越长，分数越高，但是不可以碰壁或自噬其身")
                            .show();
                    break;
            }
        }
    };
}
