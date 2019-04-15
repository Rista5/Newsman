package com.newsman.newsman.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.newsman.newsman.Entities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.adapters.NewsImageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsDisplayActivity extends AppCompatActivity {

    private RecyclerView rvPicture;
    private NewsImageListAdapter adapter;
    private List<Picture> pictureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        rvPicture = findViewById(R.id.rv_news_pictures);

        generatePictureList();
        adapter = new NewsImageListAdapter( pictureList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPicture.setLayoutManager(mLayoutManager);
        rvPicture.setItemAnimator(new DefaultItemAnimator());
        rvPicture.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
    }

    private void generatePictureList(){
        pictureList = new ArrayList<>();
        for(int i = 0; i <  5; i++) {
            pictureList.add(new Picture(i, "Picture" + i, "Picture description" + i, i, null));
        }
    }
}
