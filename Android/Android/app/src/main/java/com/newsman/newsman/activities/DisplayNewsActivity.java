package com.newsman.newsman.activities;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.Auxiliary.PopUpMenuController;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.CommentDao;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.CommentWithUsername;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.User;
import com.newsman.newsman.adapters.NewsImageListAdapter;
import com.newsman.newsman.fragments.CommentsFragment;
import com.newsman.newsman.fragments.CreateCommentFragment;
import com.newsman.newsman.fragments.FragmentStrategies.SendToRest;
import com.newsman.newsman.fragments.PicturesFragment;

import java.util.ArrayList;
import java.util.List;

public class DisplayNewsActivity extends AppCompatActivity {

    private ImageView background, overflow;
    private TextView title, postDate, lastUpdateBy, content;

    private NewsImageListAdapter adapter;

    private int newsId = -1;
    private List<Picture> pictureList;

    private CommentsFragment commentsFragment;
    private PicturesFragment picturesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
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

    private void subscribeToLiveData() {
        final LifecycleOwner mOwner = this;
        LiveData<News> liveNews = AppDatabase.getInstance(this).newsDao().getNewsById(newsId);
        liveNews.observe(this, new Observer<News>() {
            @Override
            public void onChanged(@Nullable News news) {
                title.setText(news.getTitle());
                postDate.setText(news.getLastModified().toString());
                content.setText(news.getContent());
            }
        });
        final CommentDao commentDao = AppDatabase.getInstance(this).commentDao();
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
        pictureList = new ArrayList<>();
        LiveData<List<Picture>> livePictres = AppDatabase.getInstance(this).pictureDao()
                .getPicturesForNews(newsId);
        livePictres.observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(@Nullable List<Picture> pictures) {
                picturesFragment.setPictureList(pictures);
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
