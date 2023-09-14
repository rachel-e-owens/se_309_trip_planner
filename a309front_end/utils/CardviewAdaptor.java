package com.coms309.a309front_end.utils;


import android.content.Context;

import android.view.View;
import android.widget.Toast;

import com.coms309.a309front_end.R;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CardviewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  LayoutInflater layoutInflater;
    private List<String> title;
    private List<String> data;
    private List<String> image;
    ArrayList<View.OnClickListener> liseners;



    public CardviewAdaptor(Context context, List<String> title, List<String> data, List<String> image, ArrayList<View.OnClickListener> liseners){
        this.layoutInflater = LayoutInflater.from(context);
        this.title = title;
        this.data = data;
        this.image = image;
        this.liseners = liseners;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder ViewHolder, int position) {


        // bind the textview with data received

        ViewHolder = (CardviewAdaptor.ViewHolder)  ViewHolder;


        String title = this.title.get(position);
        ((CardviewAdaptor.ViewHolder) ViewHolder).textTitle.setText(title);


        String data = this.data.get(position);
        ((CardviewAdaptor.ViewHolder) ViewHolder).textDescription.setText(data);


        Bitmap image = imageUtils.StringToBitMap(this.data.get(position));

        ((CardviewAdaptor.ViewHolder) ViewHolder).image.setOnClickListener(this.liseners.get(position));


        // similarly you can set new image for each card and descriptions
    }


    public void onBindViewHolder( ViewHolder viewHolder, int i) {





    }

    @Override
    public int getItemCount() {
        return title.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView textTitle, textDescription;
        public ImageView image;
        public String postID;
        public View.OnClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(listener);

            textTitle = itemView.findViewById(R.id.textTitle);
            textTitle.setOnClickListener(listener);
            textDescription = itemView.findViewById(R.id.textDesc);
            image = itemView.findViewById(R.id.imageView);
        }
    }





}
