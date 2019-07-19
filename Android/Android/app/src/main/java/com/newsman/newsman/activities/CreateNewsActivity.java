package com.newsman.newsman.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.Audio;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.databinding.ActivityCreateNewsBinding;

import java.util.ArrayList;

public class CreateNewsActivity extends AppCompatActivity {

    private EditText editTextNewsTitle, editTextContent;
    private Button buttonChoseBackgroud, buttonAddImage;
    private ImageView imageViewBackground;
    private RecyclerView rvImages;
    private Button buttonPostNews, buttonCancel;

    private ActivityCreateNewsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);

        setUpViews();
    }

    private void setUpViews() {
        editTextNewsTitle = findViewById(R.id.create_new_news_title);
        editTextContent = findViewById(R.id.create_new_news_content);
        buttonChoseBackgroud = findViewById(R.id.create_new_news_chose_background_image_button);
        buttonAddImage = findViewById(R.id.create_new_news_add_image_button);
        imageViewBackground = findViewById(R.id.create_new_news_background);
        rvImages = findViewById(R.id.rv_create_news_pictures);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_news);

        buttonPostNews = findViewById(R.id.create_new_news_post_news);
        buttonPostNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkValidNews()) {
                    displayToast();
                } else {

                }
            }
        });
        buttonCancel = findViewById(R.id.create_new_news_cancel);
    }

    private boolean checkValidNews() {
        if(editTextNewsTitle.getText().toString().length() > 0 &&
            editTextContent.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void displayToast() {
        Toast toast = new Toast(this);
        toast.setText(R.string.create_news_toast_message);
    }

    private News createNews() {
        News news = new News();
        news.setTitle(editTextNewsTitle.getText().toString());
        news.setContent(editTextContent.getText().toString());
        news.setComments(new ArrayList<Comment>());
        news.setId(-1);
        news.setAudioRecordings(new ArrayList<Audio>());
        news.setPictures(new ArrayList<Picture>());

        return news;
    }
}
