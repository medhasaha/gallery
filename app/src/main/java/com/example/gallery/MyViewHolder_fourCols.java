package com.example.gallery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder_fourCols extends RecyclerView.ViewHolder implements View.OnClickListener  {
    ImageView ivPhotoOnly;

    public MyViewHolder_fourCols(@NonNull View itemView) {
        super(itemView);
        itemView=itemView;

        ivPhotoOnly=(ImageView) itemView.findViewById(R.id.ivPhotoOnly);
    }

    @Override
    public void onClick(View v) {

    }

    public ImageView getIvPhotoOnly() {
        return ivPhotoOnly;
    }

}
