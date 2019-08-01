package com.example.five.priceparity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class myAdapter extends ArrayAdapter {

    private final int ImageUrl;

    public myAdapter(Context context, int headImage, List<myBean> obj){
        super(context,headImage,obj);
        ImageUrl = headImage;//这个是传入我们自己定义的界面

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final myBean mybean = (myBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.listlayout,null);//这个是实例化一个我们自己写的界面Item
        LinearLayout linearLayout = view.findViewById(R.id.ll_view);

        String url = mybean.getImageUrl();
        ImageView headImage = view.findViewById(R.id.headimage);
        TextView headText = view.findViewById(R.id.headtext);
        Glide.with(getContext()).load(url)./*override(184, 69).*/fitCenter().into(headImage);
        headText.setText(mybean.getName());

        linearLayout.setOnClickListener(new View.OnClickListener() {//检查哪一项被点击了
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"你点击了第"+position+"项"+"你选择"/*+radiotext*/,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ShowDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("game", gson.toJson(mybean));
                getContext().startActivity(intent);
            }
        });
        return view;
    }
}
