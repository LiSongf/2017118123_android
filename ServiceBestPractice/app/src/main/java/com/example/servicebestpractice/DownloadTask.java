package com.example.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer>{
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProress;

    public DownloadTask(DownloadListener listener){
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try{
            //记录已下载文件长度
            long downloadedLength = 0;
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getDownloadCacheDirectory().getPath();

            Log.d("Mylog", "directory:"+directory);

            file = new File(directory+fileName);

            Log.d("Mylog", "file:"+file);
            if(file.exists()){
                downloadedLength = file.length();
            }
            long concentLength = getContentLength(downloadUrl);
            //如果已下载字节和文件总字节相等，则说明已经下载完成了
            if (concentLength == 0){
                return TYPE_FAILED;
            }
            else if (concentLength == downloadedLength){

                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            //断点下载，指定从哪个字节开始下载
            Request request = new Request.Builder().addHeader("RANGE", "bytes="+downloadedLength +"-")
                                    .url(downloadUrl)
                                    .build();

            Response response = client.newCall(request).execute();
            if (response != null){
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                //跳过已下载的字节
                savedFile.seek(downloadedLength);
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while((len = inputStream.read(bytes))!= -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(bytes, 0, len);
                        //计算已下载百分比
                        int progress = (int) ((total + downloadedLength)*100/concentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
                if (savedFile != null){
                    savedFile.close();
                }
                if (isCanceled && file != null){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress >lastProress){
            listener.onProgress(progress);
            lastProress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default :
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}
