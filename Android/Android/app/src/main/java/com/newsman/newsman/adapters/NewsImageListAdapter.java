package com.newsman.newsman.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.R;

import java.util.List;

public class NewsImageListAdapter extends RecyclerView.Adapter<NewsImageListAdapter.NewsImageViewHolder> {

    private List<Picture> pictureItemList;

    public NewsImageListAdapter(List<Picture> pictureList){
        this.pictureItemList = pictureList;
    }

    @NonNull
    @Override
    public NewsImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_item, viewGroup, false);
        return new NewsImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsImageViewHolder newsImageViewHolder, int position) {
        Picture pictureItem = pictureItemList.get(position);
        newsImageViewHolder.title.setText(pictureItem.getName());
    }

    @Override
    public int getItemCount() {
        return pictureItemList.size();
    }


    public class NewsImageViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;

        public NewsImageViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.image_item_title);
            image = itemView.findViewById(R.id.image_item_image);
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImage() {
            return image;
        }
    }
}
