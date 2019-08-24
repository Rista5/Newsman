package com.newsman.newsman.fragments;

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
import android.widget.ImageView;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.activities.CreatePictureActivity;
import com.newsman.newsman.adapters.PicturesListAdapter;

import java.util.List;

public class PicturesFragment extends Fragment {

    private List<Picture> pictureList;
    private PicturesListAdapter adapter;
    private RecyclerView rvPicture;
    private ImageView addPicture;
    private int newsId;
    private boolean sendToRest;

    public static PicturesFragment newInstance(int newsId, List<Picture> pictures, boolean sendToRest) {
        PicturesFragment pf = new PicturesFragment();
        pf.pictureList = pictures;
        pf.newsId = newsId;
        pf.sendToRest = sendToRest;
        return pf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        rvPicture = rootView.findViewById(R.id.rv_news_pictures);
        addPicture = rootView.findViewById(R.id.news_item_add_picture);
        setUpPictureAdapter();
        addButtonListener();
        return rootView;
    }

    private void setUpPictureAdapter() {
        adapter = new PicturesListAdapter(getContext(), pictureList, sendToRest);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPicture.setLayoutManager(mLayoutManager);
        rvPicture.setItemAnimator(new DefaultItemAnimator());
        rvPicture.setAdapter(adapter);
    }

    public void setPictureList(List<Picture> pictures) {
        this.pictureList = pictures;
        adapter.setPictureList(pictures);
    }

    public List<Picture> getPictureList(){
        return adapter.getPictureList();
    }

    private void addButtonListener() {
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, Constant.PICTURE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)
            return;
        Bundle extras = data.getExtras();
        if(extras != null && extras.get(Constant.PICTURE_BUNDLE_KEY) != null &&
                extras.get(Constant.PICTURE_BUNDLE_KEY) instanceof Picture) {
            Picture picture = (Picture) extras.get(Constant.PICTURE_BUNDLE_KEY);
            adapter.addPicture(picture);
        }
    }
}