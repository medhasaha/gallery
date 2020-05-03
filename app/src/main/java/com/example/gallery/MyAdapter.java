package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final ArrayList<ModelClass> list;


    public MyAdapter(Context context, ArrayList<ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater1 = LayoutInflater.from((parent.getContext()));
        View view = inflater1.inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
//        Log.i("URL position", position + "");
        MyViewHolder myHolder = (MyViewHolder) holder;
//        myHolder.getTextView().setText(position + "");
        Picasso.with(context).load(list.get(position).getWebformatURL()).into(myHolder.getIvPhoto());
        if(list.get(position).getUserImageURL().isEmpty() == false) {
            Picasso.with(context).load(list.get(position).getUserImageURL()).into(myHolder.getIvUserImage());
        }
        myHolder.getTvUser().setText(list.get(position).getUser());
        myHolder.getTvFavorites().setText(list.get(position).getFavorites().toString());
//        myHolder.getTvViews().setText(list.get(position).getViews().toString());
        myHolder.getTvLikes().setText(list.get(position).getLikes().toString());
    }

    @Override
    public int getItemCount() {
//        Log.i("URL size", list.size() + "");
        return list.size();

    }
}
