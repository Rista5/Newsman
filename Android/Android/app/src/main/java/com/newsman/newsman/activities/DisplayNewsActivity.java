package com.newsman.newsman.activities;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.Auxiliary.PopUpMenuController;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.CommentWithUsername;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.User;
import com.newsman.newsman.fragments.CommentsFragment;
import com.newsman.newsman.fragments.PicturesFragment;

import java.util.ArrayList;
import java.util.List;

public class DisplayNewsActivity extends AppCompatActivity {

    private ImageView background, overflow;
    private TextView title, postDate, lastUpdateBy, content;

    private int newsId = Constant.INVALID_NEWS_ID;
    private Context mContext;

    private CommentsFragment commentsFragment;
    private PicturesFragment picturesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_BUNDLE_KEY);
        }

        setUpViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }

        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpMenuController.showMenu(getApplicationContext(), v, newsId);
            }
        });

        inflatePicturesFragment();
        inflateCommentsFragment();
        subscribeToLiveData();
        getPictures();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK &&
                requestCode == Constant.PICTURE_REQUEST_CODE) {
            picturesFragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void subscribeToLiveData() {
        final LifecycleOwner mOwner = this;
        LiveData<News> liveNews = AppDatabase.getInstance(this).newsDao().getNewsById(newsId);
        liveNews.observe(this, new Observer<News>() {
            @Override
            public void onChanged(@Nullable News news) {
                title.setText(news.getTitle());
                postDate.setText(news.getLastModified().toString());
                content.setText(news.getContent());
                //TODO razmisli da li moze ovo bolje
                if(news.getBackgroundId() != Constant.INVALID_PICTURE_ID)
                    background.setImageBitmap(PictureLoader.loadPictureData(mContext, news.getBackgroundId()));
            }
        });
        LiveData<List<Comment>> liveComments =
                AppDatabase.getInstance(this).commentDao().getCommentsForNews(newsId);
        //TODO moze li ovo bolje, pogotovo ovo sa username-om, mozda da postane ugnjezdeni entitet
        final UserDao userDao = AppDatabase.getInstance(this).userDao();
        liveComments.observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> commentList) {
                if(commentsFragment!=null) {
                    List<CommentWithUsername> commentUserList = new ArrayList<>(commentList.size());
                    for(Comment c:commentList){
                        User user = userDao.getUserByIdNonLive(c.getCreatedById());
                        commentUserList.add(new CommentWithUsername(c, user.getUsername()));
                    }
                    commentsFragment.setCommentList(commentUserList);
                }
            }
        });
    }

    private void getPictures(){
        LiveData<List<Picture>> livePictres = AppDatabase.getInstance(this).pictureDao()
                .getPicturesForNews(newsId);
        final Context mContext = this;
        livePictres.observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(@Nullable List<Picture> pictures) {
                picturesFragment.setPictureList(PictureLoader.loadPictureListData(mContext, pictures));
            }
        });
    }

    private void inflateCommentsFragment() {
        commentsFragment = CommentsFragment.newInstance(newsId, new ArrayList<CommentWithUsername>());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.news_item_comments_fragment, commentsFragment)
                .commit();
    }

    private void inflatePicturesFragment() {
        picturesFragment = PicturesFragment.newInstance(newsId, new ArrayList<Picture>(), true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.news_item_pictures_fragment, picturesFragment)
                .commit();
    }

    private void setUpViews() {
        background = findViewById(R.id.news_item_img);
        overflow = findViewById(R.id.news_item_overflow_list);
        title = findViewById(R.id.news_item_title);
        postDate = findViewById(R.id.news_item_post_date_value);
        lastUpdateBy = findViewById(R.id.news_item_last_user_update_value);
        content = findViewById(R.id.news_item_text_content);
    }
}
