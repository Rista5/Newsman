package com.newsman.newsman.activities;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.CommentDao;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.User;
import com.newsman.newsman.adapters.NewsImageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsDisplayActivity extends AppCompatActivity {

    private ImageView background, overflow;
    private TextView title, postDate, lastUpdateBy, content;
    private RecyclerView rvPicture;
    private LinearLayout llComments;
    private NewsImageListAdapter adapter;

    private int newsId = -1;
    private News news;
    private List<Comment> commentList;
    private List<Picture> pictureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_EXTRA_ID_KEY);
        }

        setUpViews();
        generatePictureList();
        setUpPictureAdapter();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        final LifecycleOwner mOwner = this;
        LiveData<News> liveNews = AppDatabase.getInstance(this).newsDao().loadNewsById(newsId);
        liveNews.observe(this, new Observer<News>() {
            @Override
            public void onChanged(@Nullable News news) {
                title.setText(news.getTitle());
                postDate.setText(news.getLastModified().toString());
                content.setText(news.getContent());
            }
        });
        final CommentDao commentDao = AppDatabase.getInstance(this).commentDao();
        LiveData<List<Comment>> liveComments = AppDatabase.getInstance(this).commentDao().getCommentsForNews(newsId);
        //TODO moze li ovo bolje, pogotovo ovo sa username-om
        final UserDao userDao = AppDatabase.getInstance(this).userDao();
        liveComments.observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> commentList) {
                llComments.removeAllViews();
                for(Comment c: commentList) {
                    //TODO remove debug comment data
                    commentDao.getCommentByIdNonLive(c.getId());
                    View view = getLayoutInflater().inflate(R.layout.comment_item, null);
                    final TextView username = view.findViewById(R.id.comment_item_username);
                    TextView postDate = view.findViewById(R.id.comment_item_post_date);
                    TextView content = view.findViewById(R.id.comment_item_content);
                    postDate.setText(c.getPostDate().toString());
                    content.setText(c.getContent());
                    LiveData<User> user = userDao.loadUserById(c.getCreatedById());
                    user.observe(mOwner, new Observer<User>() {
                        @Override
                        public void onChanged(@Nullable User user) {
                            username.setText(user.getUsername());
                        }
                    });
                    llComments.addView(view);
                }
            }
        });
    }

    private void setUpViews() {
        background = findViewById(R.id.news_item_img);
        overflow = findViewById(R.id.news_item_overflow_list);
        title = findViewById(R.id.news_item_title);
        postDate = findViewById(R.id.news_item_post_date_value);
        lastUpdateBy = findViewById(R.id.news_item_last_user_update_value);
        content = findViewById(R.id.news_item_text_content);

        rvPicture = findViewById(R.id.rv_news_pictures);
        llComments = findViewById(R.id.news_item_comments_list);
    }

    private void setUpPictureAdapter() {
        adapter = new NewsImageListAdapter(pictureList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPicture.setLayoutManager(mLayoutManager);
        rvPicture.setItemAnimator(new DefaultItemAnimator());
        rvPicture.setAdapter(adapter);
    }

    private void generatePictureList(){
        pictureList = new ArrayList<>();
        for(int i = 0; i <  5; i++) {
            pictureList.add(new Picture(i, "Picture" + i, "Picture description" + i, i, null));
        }
    }
}
