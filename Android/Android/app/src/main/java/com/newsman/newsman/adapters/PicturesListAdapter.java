package com.newsman.newsman.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureConverter;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.picture_management.BitmapOneTimeObserver;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Delete;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Post;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Put;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.rest_connection.WriteJson.WritePicture;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.activities.ImageDisplayActivity;

import java.util.List;
import java.util.Observable;

public class PicturesListAdapter extends RecyclerView.Adapter<PicturesListAdapter.NewsImageViewHolder> {

    private List<Picture> pictureList;
    private Context context;
    private boolean sendToRest;

    public PicturesListAdapter(Context context, List<Picture> pictureList, boolean sendToRest){
        this.pictureList = pictureList;
        this.context = context;
        this.sendToRest = sendToRest;
    }

    @NonNull
    @Override
    public NewsImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_item, viewGroup, false);
        return new NewsImageViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsImageViewHolder newsImageViewHolder, int position) {
        Picture pictureItem = pictureList.get(position);
        newsImageViewHolder.title.setText(pictureItem.getName());
        //TODO razmisli da li moze ovo bolje
//        BitmapObserver observer = new BitmapObserver(newsImageViewHolder.imageView);
//        Observable observable = BitmapCache.getInstance().getBitmap(context, pictureItem.getId(), pictureItem.getBelongsToNewsId());
//        observable.addObserver(observer);
          BitmapOneTimeObserver observer = new BitmapOneTimeObserver(newsImageViewHolder.imageView);
          Observable observable = BitmapCache.getInstance().getBitmap(context, pictureItem.getId(), pictureItem.getBelongsToNewsId());
          observable.addObserver(observer);

//        if(pictureItem.getPictureData() != null)
//            newsImageViewHolder.imageView.setImageBitmap(pictureItem.getPictureData());
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public void addPicture(Picture picture) {
        if(sendToRest){
            new RestConnector(new Put(new WritePicture(picture)), Constant.PICTURE_ROUTE)
                    .execute();
        } else {
            int position = pictureList.size();
            pictureList.add(position, picture);
            notifyItemInserted(position);
        }
    }

    public void updatePicture(int position, Picture picture) {
        if(sendToRest){
            new RestConnector(new Post(new WritePicture(picture)), Constant.PICTURE_ROUTE)
                    .execute();
        } else {
            pictureList.set(position, picture);
            notifyItemChanged(position);
        }

    }

    public void removePicture(int position) {
        if(sendToRest){
            Picture picture = pictureList.get(position);
            new RestConnector(new Delete(picture.getId()), Constant.PICTURE_ROUTE)
                    .execute();
        } else {
            pictureList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setPictureList(List<Picture> list){
        pictureList.clear();
        pictureList.addAll(list);
        notifyDataSetChanged();
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public class NewsImageViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView imageView;
        private ImageView removePicture;

        private PicturesListAdapter adapter;

        NewsImageViewHolder(@NonNull View itemView, PicturesListAdapter adapter){
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
                    int pos = getAdapterPosition();
                    adapter.removePicture(pos);
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
