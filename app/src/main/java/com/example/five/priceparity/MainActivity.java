package com.example.five.priceparity;

import android.content.Intent;
import android.os.StrictMode;
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

    private String post(String url, String gameName) throws IOException{
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
