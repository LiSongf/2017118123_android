package com.example.mydemo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.net.Uri;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG="Main";
    private Button baidu;
    private Button tel;
    private Button geo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baidu = findViewById(R.id.btBaidu);
        tel = findViewById(R.id.btTel);
        geo = findViewById(R.id.btGeo);
        baidu.setOnClickListener(this);
        tel.setOnClickListener(this);
        geo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btBaidu:
                onClickBaidu(view);
                break;
            case R.id.btTel:
                onClickTel(view);
                break;
            case R.id.btGeo:
                onClickGeo(view);
                break;
            default:
                break;
        }

    }

    private void onClickBaidu(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.baidu.com"));
        startActivity(intent);
    }

    private  void onClickTel(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivit
    }

    private  void onClickGeo(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"));
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
