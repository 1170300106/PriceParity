package com.example.five.priceparity;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //private String data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};

    private List<myBean> myBeanList = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();



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
        Toast toast=Toast.makeText(MainActivity.this,"Begin json parse",Toast.LENGTH_SHORT    );
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAdapter adapter = new myAdapter(MainActivity.this,R.layout.listlayout,myBeanList);

        listView.setAdapter(adapter);

    }

    private void init() throws IOException {//初始化数据
//        Scanner sc = new Scanner("C:\Users\Raymo\Desktop\data.json");
//        for(int i = 0; i < 15; i++){
//            myBean bean = new myBean("aa",20.0,"https://media.st.dl.bscstorage.net/steam/apps/457140/capsule_184x69_schinese.jpg?t=1564505458");
//            myBeanList.add(bean);
//        }

        Toast toast2 = Toast.makeText(MainActivity.this, "Begin request", Toast.LENGTH_SHORT);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();


        Toast toast3 = Toast.makeText(MainActivity.this, "End request", Toast.LENGTH_SHORT);
        toast3.setGravity(Gravity.CENTER, 0, 0);
        toast3.show();


        Toast toast4 = Toast.makeText(MainActivity.this, "Begin try", Toast.LENGTH_SHORT);
        toast4.setGravity(Gravity.CENTER, 0, 0);
        toast4.show();


        Gson gson = new Gson();

        Log.i("main", "Begin json parse");
        Toast toast = Toast.makeText(MainActivity.this, "Begin json parse", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


        myBeanList = Arrays.asList(gson.fromJson(run("http://192.168.43.52:8080/gamelist"), myBean[].class));

        Log.i("main", "Finish json parse");
        int len = myBeanList.size();
        for (int i = 0; i < len; i++) {
            myBean tmp = myBeanList.get(i);
            Log.i("main", tmp.getName() + tmp.getPrice() + tmp.getImageUrl());
            Toast toast1 = Toast.makeText(MainActivity.this, "Begin json parse", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }




//
//        myBean bean2 = new myBean("Grand Theft Auto V", 59, "https://media.st.dl.bscstorage.net/steam/apps/271590/header.jpg?t=1563930957");
//        myBeanList.add(bean2);
//
//        myBean bean3 = new myBean("Oxygen Not Included",38, "https://media.st.dl.bscstorage.net/steam/apps/457140/header.jpg?t=1564505458");
//        myBeanList.add(bean3);

//        myBean bean4 = new myBean("hh",R.mipmap.ic_launcher);
//        myBeanList.add(bean4);
//
//        myBean bean5 = new myBean("dd",R.mipmap.ic_launcher);
//        myBeanList.add(bean5);
//
//        myBean bean6 = new myBean("cc",R.mipmap.ic_launcher);
//        myBeanList.add(bean6);
//
//        myBean bean7 = new myBean("bb",R.mipmap.ic_launcher);
//        myBeanList.add(bean7);
//        myBean bean8 = new myBean("jj",R.mipmap.ic_launcher);
//        myBeanList.add(bean8);
//        myBean bean9 = new myBean("kk",R.mipmap.ic_launcher);
//        myBeanList.add(bean9);
    }
}
