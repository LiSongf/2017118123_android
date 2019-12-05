package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(){
            Log.d("MyService", "执行 startDownload");
        }

        public int getProgress(){
            Log.d("MyService", "执行 getProgress");
            return 0;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MyService","执行 onCreate");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("MyService","执行 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService","执行 onDestroy");
    }
}
