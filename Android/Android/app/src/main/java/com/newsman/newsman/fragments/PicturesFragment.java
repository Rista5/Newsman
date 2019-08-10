package com.newsman.newsman.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.activities.CreatePictureActivity;
import com.newsman.newsman.adapters.NewsImageListAdapter;

import java.util.List;

public class PicturesFragment extends Fragment {

    private List<Picture> pictureList;
    private NewsImageListAdapter adapter;
    private RecyclerView rvPicture;
    private Button addPictureButton;
    private Context context;
    private int newsId;

    public static PicturesFragment newInstance(Context context, int newsId, List<Picture> pictures) {
        PicturesFragment pf = new PicturesFragment();
        pf.pictureList = pictures;
        pf.context = context;
        pf.newsId = newsId;
        return pf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        rvPicture = rootView.findViewById(R.id.rv_news_pictures);
        addPictureButton = rootView.findViewById(R.id.news_item_create_picture_button);
        setUpPictureAdapter();
        return rootView;
    }

    private void setUpPictureAdapter() {
        adapter = new NewsImageListAdapter(context, pictureList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvPicture.setLayoutManager(mLayoutManager);
        rvPicture.setItemAnimator(new DefaultItemAnimator());
        rvPicture.setAdapter(adapter);
    }

    public void setPictureList(List<Picture> pictures) {
        pictureList.clear();
        pictureList.addAll(pictures);
        adapter.notifyDataSetChanged();
    }

    private void addButtonListener() {
        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatePictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.NEWS_EXTRA_ID_KEY, newsId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
