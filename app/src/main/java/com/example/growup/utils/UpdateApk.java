package com.example.growup.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.growup.BuildConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateApk {

    private final Activity activity;

    public UpdateApk(Activity activity) {
        this.activity = activity;
    }

    public void checkForUpdate() {
        String updateCheckUrl = "http://10.0.2.2:8000/pt-br/api/v1/apk-version/";

        new Thread(() -> {
            try {
                URL url = new URL(updateCheckUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject json = new JSONObject(sb.toString());
                    int latestVersion = json.getInt("latestVersion");
                    String apkUrl = json.getString("apk_url");

                    if (latestVersion > BuildConfig.VERSION_CODE) {
                        activity.runOnUiThread(() -> showUpdateDialog(apkUrl));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showUpdateDialog(String apkUrl) {
        new AlertDialog.Builder(activity)
                .setTitle("Atualização disponível")
                .setMessage("Uma nova versão do app está disponível. Deseja atualizar agora?")
                .setPositiveButton("Atualizar", (dialog, which) -> downloadAndInstallApk(apkUrl))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @SuppressLint({"UpdateApk", "NewApi"})
    private void downloadAndInstallApk(String apkUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setTitle("Baixando atualização");
        request.setDescription("Aguardando download...");
        request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Uri uri = manager.getUriForDownloadedFile(downloadId);
                installApk(uri);
                activity.unregisterReceiver(this);
            }
        };

        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED);
    }

    private void installApk(Uri apkUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(intent);
    }
}
