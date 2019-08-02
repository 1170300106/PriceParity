package com.example.five.priceparity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //private String data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};

    private List<myBean> myBeanList = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charsest=utf-8");
    private Context context = this;
    private boolean isRun = false;


    private String post(String url, String gameName) throws IOException {
        RequestBody body = RequestBody.create(JSON, gameName);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ListView listView = (ListView) findViewById(R.id.listview);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAdapter adapter = new myAdapter(MainActivity.this,R.layout.listlayout,myBeanList);

        listView.setAdapter(adapter);

        Intent intent = getIntent();
        isRun = intent.getBooleanExtra("flag", false);

        if(!isRun) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10 * 1000);
                        Gson gson = new Gson();

                        String get = post("http://192.168.43.52:8080/sale", getAndroidId(context));
                        if(get.length() == 0){
                            return;
                        }

                        myBean saleGame = gson.fromJson(get, myBean.class);

                        createNotificationChannel();

                        // Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(context, ShowDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("game", gson.toJson(saleGame));
                        intent.putExtra("flag", true);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "sale")
                                .setSmallIcon(R.drawable.sale)
                                .setContentTitle(saleGame.getName())
                                .setContentText("正在热卖，快来康康！")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);


                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                        // notificationId is a unique int for each notification that you must define
                        notificationManager.notify(0, builder.build());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            isRun = true;
        }
    }

    public static String getAndroidId (Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return ANDROID_ID;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("sale", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /** Called when the user taps the go button */
    public void showMessage(View view) throws IOException{
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        Intent intent = new Intent(this, ShowSearchActivity.class);
        intent.putExtra("searchgame", message);
        startActivity(intent);
    }

    private void init() throws IOException {//初始化数据
        Gson gson = new Gson();
        myBeanList = Arrays.asList(gson.fromJson(run("http://192.168.43.52:8080/gamelist"), myBean[].class));
    }
}
