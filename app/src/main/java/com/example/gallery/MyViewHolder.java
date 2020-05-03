package com.example.gallery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    ImageView ivPhoto;
    TextView tvUser, tvLikes, tvViews, tvFavorites ;
    CircleImageView ivUserImage ;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView=itemView;

        ivPhoto=(ImageView) itemView.findViewById(R.id.ivPhoto);
        tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
//        tvViews = (TextView) itemView.findViewById(R.id.tvViews);
        tvFavorites = (TextView) itemView.findViewById(R.id.tvFavorites);
        ivUserImage = (CircleImageView) itemView.findViewById((R.id.ivUserImage));
    }

    @Override
    public void onClick(View v) {

    }

    public ImageView getIvPhoto() {
        return ivPhoto;
    }

    public TextView getTvUser() {
        return tvUser;
    }

    public TextView getTvFavorites() {
        return tvFavorites;
    }

    public TextView getTvLikes() {
        return tvLikes;
    }

    public TextView getTvViews() {
        return tvViews;
    }

    public CircleImageView getIvUserImage() {
        return ivUserImage;
    }

}
