package com.newsman.newsman.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;
import com.newsman.newsman.fragments.CommentsFragment;
import com.newsman.newsman.fragments.CreateNewsFragment;
import com.newsman.newsman.fragments.PicturesFragment;

import java.util.List;

public class UpdateNewsActivity extends AppCompatActivity {

    private int newsId;
    private CreateNewsFragment createNewsFragment;
    private PicturesFragment picturesFragment;
    private CommentsFragment commentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        // verovatno je najbolje da se posalje vest umesto id, ali za sad nek ide iz db
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_EXTRA_ID_KEY);
            setFragments();
        }
    }

    private void setFragments() {
        News news = AppDatabase.getInstance(this).newsDao().loadNewsByIdNonLive(newsId);
        createNewsFragment = CreateNewsFragment.newInstance(SimpleNews.getSimpleNews(news, this));
        List<Picture> pictures = AppDatabase.getInstance(this).pictureDao()
                .getPicturesForNewsNonLive(newsId);
        picturesFragment = PicturesFragment.newInstance(this, newsId, pictures);
        List<Comment> comments = AppDatabase.getInstance(this).commentDao()
                .getCommentsForNewsNonLive(newsId);
        commentsFragment = CommentsFragment.newInstance(newsId, comments);
    }
}
