package com.example.downloadmanager_rt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private long downloadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        ImageButton download = findViewById(R.id.btnPdf);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginDownload();
            }
        });
    }

    private void beginDownload(){
        File file = new File(getExternalFilesDir(null), "Dummy");

        DownloadManager.Request request = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request = new DownloadManager.Request(Uri.parse("www.nasa.com"))
                    .setTitle("PRUEBA")
                    .setDescription("Downloading")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
        }
        else {
            request = new DownloadManager.Request(Uri.parse("www.nasa.com"))
                    .setTitle("PRUEBA")
                    .setDescription("Downloading")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationUri(Uri.fromFile(file))
                    .setAllowedOverRoaming(true);
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = (int) downloadManager.enqueue(request);
    }

    //  Chequear si la descarga está completada

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadId==id){
                Toast.makeText(MainActivity.this, "Descarga Completada", Toast.LENGTH_SHORT).show();
            }
        }
    };

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }
}