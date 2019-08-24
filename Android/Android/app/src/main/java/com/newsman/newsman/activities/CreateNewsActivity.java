package com.newsman.newsman.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.REST.ConnectionStrategy.Put;
import com.newsman.newsman.REST.RestConnector;
import com.newsman.newsman.REST.WriteJson.WriteNews;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;
import com.newsman.newsman.fragments.CreateNewsFragment;
import com.newsman.newsman.fragments.PicturesFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;

public class CreateNewsActivity extends AppCompatActivity {

    private CreateNewsFragment createNewsFragment;
    private PicturesFragment picturesFragment;
    private Button buttonPostNews, buttonCancel;

    private Bitmap backgroundPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);

        setFragments();
        setUpViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Constant.REQUEST_LOAD_IMAGE ||
                requestCode == Constant.REQUEST_IMAGE_CAPTURE) {
            createNewsFragment.onActivityResult(requestCode, resultCode, data);
            return;
        } else if (requestCode == Constant.PICTURE_REQUEST_CODE) {
            picturesFragment.onActivityResult(requestCode, resultCode, data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpViews() {
        buttonPostNews = findViewById(R.id.create_news_post_news);
        buttonPostNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news = createNews();
                if(!checkValidNews(news)) {
                    displayToast();
                } else {
                    new RestConnector(new Put(new WriteNews(news, backgroundPic)), Constant.createNewsRoute())
                            .execute();
                    finish();
                }
            }
        });
        buttonCancel = findViewById(R.id.create_news_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setFragments() {
        SimpleNews news = new SimpleNews(-1, "", "", new Date(), null,-1);
        createNewsFragment = CreateNewsFragment.newInstance(news);
        picturesFragment = PicturesFragment.newInstance(-1, new ArrayList<Picture>(), false);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.create_news_news_frame, createNewsFragment)
                .add(R.id.create_news_pictures_frame, picturesFragment)
                .commit();
    }

    private boolean checkValidNews(News news) {
        if(news.getTitle().equals("") || news.getContent().equals(""))
            return false;
        else
            return true;
    }

    private void displayToast() {
        Toast toast = Toast.makeText(this, R.string.create_news_toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private News createNews() {
        News news = new News();
        SimpleNews simpleNews = createNewsFragment.getNews();
        SimpleNews.populateNews(news, simpleNews);
        backgroundPic = simpleNews.getBackgroundPicture();
        List<Picture> pictures = picturesFragment.getPictureList();
        news.setPictures(pictures);
        return news;
    }
}
