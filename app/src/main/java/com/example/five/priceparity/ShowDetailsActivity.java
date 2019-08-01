package com.example.five.priceparity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowDetailsActivity extends AppCompatActivity {

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

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Intent intent = getIntent();
        String gameobj = intent.getStringExtra("game");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Gson gson = new Gson();
        Game game = new Game("GTA5");
        try {
           game = gson.fromJson(post("http://192.168.43.52:8080/game",gameobj), Game.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast toast2 = Toast.makeText(this, game.getName()+"\n"+game.getPrices()[0]+"\n"+game.getContents()+"\n"+game.getImageUrl()+"\n"+game.getUrl(), Toast.LENGTH_SHORT);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        showMyToast(toast2,10000);
    }


}
