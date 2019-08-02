package com.example.five.priceparity;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowSearchActivity extends AppCompatActivity {

    private List<myBean> myBeanList = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charsest=utf-8");


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
        Intent intent = getIntent();
        String search = intent.getStringExtra("searchgame");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ListView listView = (ListView) findViewById(R.id.listview);
        try {
            Gson gson = new Gson();
            myBeanList = Arrays.asList(gson.fromJson(post("http://192.168.43.52:8080/gamelist", search), myBean[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAdapter adapter = new myAdapter(ShowSearchActivity.this,R.layout.listlayout,myBeanList);

        listView.setAdapter(adapter);
    }

    @Override
    //安卓重写返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("flag", true);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
