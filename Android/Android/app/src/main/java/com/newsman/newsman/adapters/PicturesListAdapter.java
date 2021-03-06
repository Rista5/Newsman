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
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.picture_management.BitmapObservable;
import com.newsman.newsman.rest_connection.rest_connectors.PictureConnector;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.picture_management.BitmapObserver;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.activities.ImageDisplayActivity;
import com.newsman.newsman.thread_management.AppExecutors;

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
        newsImageViewHolder.pictureId = pictureItem.getId();
        newsImageViewHolder.newsId = pictureItem.getBelongsToNewsId();
        newsImageViewHolder.title.setText(pictureItem.getName());
        BitmapObservable observable = BitmapCache.getInstance().getBitmapObservable(context, pictureItem.getId(), pictureItem.getBelongsToNewsId());
        newsImageViewHolder.observer = new BitmapObserver(observable, newsImageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    @Override
    public void onViewRecycled(@NonNull NewsImageViewHolder holder) {
        holder.observer.removeObserver();
        super.onViewRecycled(holder);
    }

    public void addPicture(Picture picture) {
        if(sendToRest){
            Bitmap bmp = BitmapCache.getInstance().getBitmapObservable(context,
                    picture.getId(), picture.getBelongsToNewsId()).getBitmap();
            AppExecutors.getInstance().getNetworkIO()
                    .execute(PictureConnector.savePicture(context, picture, bmp));
        } else {
            int position = pictureList.size();
            pictureList.add(position, picture);
            notifyItemInserted(position);
        }
    }

    public void updatePicture(int position, Picture picture) {
        if(sendToRest){
            Bitmap bmp = BitmapCache.getInstance().getBitmapObservable(context,
                    picture.getId(), picture.getBelongsToNewsId()).getBitmap();
            AppExecutors.getInstance().getNetworkIO()
                    .execute(PictureConnector.savePicture(context, picture, bmp));
        } else {
            pictureList.set(position, picture);
            notifyItemChanged(position);
        }

    }

    public void removePicture(int position) {
        if(sendToRest){
            Picture picture = pictureList.get(position);
            AppExecutors.getInstance().getNetworkIO()
                    .execute(PictureConnector.deletePicture(context, picture.getId()));
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

        private int pictureId = Constant.INVALID_PICTURE_ID;
        private int newsId = Constant.INVALID_NEWS_ID;
        private TextView title;
        private ImageView imageView;
        private ImageView removePicture;
        private BitmapObserver observer;

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
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.IMAGE_DISPLAY_KEY, pictureId);
                    bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            if(LoginState.getInstance().getUserId() == Constant.INVALID_USER_ID) {
                removePicture.setVisibility(View.INVISIBLE);
            } else {
                removePicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        adapter.removePicture(pos);
                    }
                });
            }
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
