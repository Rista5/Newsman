package com.newsman.newsman.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.activities.ImageDisplayActivity;

import java.util.List;

public class NewsImageListAdapter extends RecyclerView.Adapter<NewsImageListAdapter.NewsImageViewHolder> {

    private List<Picture> pictureList;
    private Context context;

    public NewsImageListAdapter(Context context, List<Picture> pictureList){
        this.pictureList = pictureList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_item, viewGroup, false);
        return new NewsImageViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsImageViewHolder newsImageViewHolder,
                                 @SuppressLint("RecyclerView") int position) {
        Picture pictureItem = pictureList.get(position);
        newsImageViewHolder.title.setText(pictureItem.getName());
        newsImageViewHolder.imageView.setImageBitmap(
                PictureConverter.getBitmap(pictureItem.getPictureData()));
        newsImageViewHolder.picturePosition = position;
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public void addPicture(Picture picture) {
        int position = pictureList.size();
        pictureList.add(position, picture);
        notifyItemInserted(position);
    }

    public void updatePicture(int position, Picture picture) {
        if(pictureList.get(position)!= null){
            pictureList.set(position, picture);
            notifyItemChanged(position);
        }
    }

    public void removePicture(int position) {
        pictureList.remove(position);
        notifyItemRemoved(position);
    }

    public void setPictureList(List<Picture> list){
        pictureList.clear();
        pictureList.addAll(list);
        notifyDataSetChanged();
    }


    public class NewsImageViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView imageView;
        private ImageView removePicture;

        private NewsImageListAdapter adapter;
        private int picturePosition;

        NewsImageViewHolder(@NonNull View itemView, NewsImageListAdapter adapter){
            super(itemView);
            this.adapter = adapter;
            title = itemView.findViewById(R.id.image_item_title);
            imageView = itemView.findViewById(R.id.image_item_image);
            removePicture = itemView.findViewById(R.id.image_item_remove);
            setClickListeners();
        }

        private void setClickListeners() {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageDisplayActivity.class);

                    Bitmap bmp = PictureConverter.getImageViewBitmap(imageView);
                    byte[] data = PictureConverter.getBitmapBytes(bmp);

                    Bundle bundle = new Bundle();
                    bundle.putByteArray(Constant.IMAGE_DISPLAY_KEY, data);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            removePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removePicture(picturePosition);
                }
            });
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
