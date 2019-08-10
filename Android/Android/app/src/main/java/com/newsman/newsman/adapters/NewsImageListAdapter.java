package com.newsman.newsman.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class NewsImageListAdapter extends RecyclerView.Adapter<NewsImageListAdapter.NewsImageViewHolder> {

    private List<Picture> pictureItemList;
    private Context context;

    public NewsImageListAdapter(Context context, List<Picture> pictureList){
        this.pictureItemList = pictureList;
        this.context = context;
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
        newsImageViewHolder.imageView.setImageBitmap(
                PictureConverter.getBitmap(pictureItem.getPictureData()));
    }

    @Override
    public int getItemCount() {
        return pictureItemList.size();
    }


    public class NewsImageViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView imageView;

        NewsImageViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.image_item_title);
            imageView = itemView.findViewById(R.id.image_item_image);

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
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
