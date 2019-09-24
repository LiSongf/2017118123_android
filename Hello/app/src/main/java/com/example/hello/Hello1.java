package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Hello1 extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG="Hello1";
    private Button hello1;
    private Button hello2;
    private Button hello3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Hello1");
        setContentView(R.layout.activity_main);
        hello1 = findViewById(R.id.btHello1);
        hello2 = findViewById(R.id.btHello2);
        hello3 = findViewById(R.id.btHello3);
        //注册监听器
        hello1.setOnClickListener(this);
        hello2.setOnClickListener(this);
        hello3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btHello1:
                onClickHello1(view);
                break;
            case R.id.btHello2:
                onClickHello2(view);
                break;
            case R.id.btHello3:
                onClickHello3(view);
                break;
            default:
                break;
        }
    }

    private void onClickHello1(View view) {
        Intent intent = new Intent(Hello1.this,Hello1.class);
        startActivity(intent);
    }

    private void onClickHello2(View view) {
        Intent intent = new Intent(Hello1.this,Hello2.class);
        startActivity(intent);
    }

    private void onClickHello3(View view) {
        Intent intent = new Intent(Hello1.this,Hello3.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }
}
