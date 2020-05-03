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

public class MyAdapter_fourCols extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final ArrayList<ModelClass> list;
    private int noOfCols;


    public MyAdapter_fourCols(Context context, ArrayList<ModelClass> list, int noOfCols) {
        this.context = context;
        this.list = list;
        this.noOfCols = noOfCols;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater1 = LayoutInflater.from((parent.getContext()));
        View view = inflater1.inflate(R.layout.row_layout_four_cols, parent, false);
        return new MyViewHolder_fourCols(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder_fourCols myHolder = (MyViewHolder_fourCols) holder;
        Picasso.with(context).load(list.get(position).getWebformatURL()).into(myHolder.getIvPhotoOnly());
    }

    @Override
    public int getItemCount() {
//        Log.i("URL size", list.size() + "");
        return list.size();

    }
}
