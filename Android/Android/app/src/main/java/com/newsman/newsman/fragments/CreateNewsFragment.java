package com.newsman.newsman.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.SimpleNews;

public class CreateNewsFragment extends Fragment {
    private EditText title;
    private EditText content;
    private ImageView backgroundImage, gallery, capture;
    private SimpleNews news;

    public static CreateNewsFragment newInstance(SimpleNews news){
        CreateNewsFragment cnf = new CreateNewsFragment();
        cnf.news = news;
        return cnf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_news, container, false);
        title = rootView.findViewById(R.id.create_new_news_title);
        content = rootView.findViewById(R.id.create_new_news_content);
        backgroundImage = rootView.findViewById(R.id.create_new_news_background);
        gallery = rootView.findViewById(R.id.create_new_news_pick_gallery);
        capture = rootView.findViewById(R.id.create_new_news_capture_photo);
        setViewsContent();
        setImageListeners();
        return rootView;
    }

    private void setViewsContent(){
        title.setText(news.getTitle());
        content.setText(news.getContent());
        backgroundImage.setImageBitmap(news.getBackgroundPicture());
    }

    public void setNews(SimpleNews news) {
        this.news = news;
        setViewsContent();
    }

    public SimpleNews getNews() {
        String newsTitle = title.getText().toString();
        String newsContent = title.getText().toString();
        Bitmap background = ((BitmapDrawable)backgroundImage.getDrawable()).getBitmap();
        return new SimpleNews(news.getId(), newsTitle, newsContent,
                news.getLastModified(), background);
    }

    private void setImageListeners(){
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureLoader.loadPictureFromGallery(getContext());
            }
        });
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureLoader.capturePhoto(getContext());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bmp = PictureLoader.getResultingBitmap(requestCode, resultCode, data, getContext());
        if(bmp!=null){
            backgroundImage.setImageBitmap(bmp);
        }
    }
}
