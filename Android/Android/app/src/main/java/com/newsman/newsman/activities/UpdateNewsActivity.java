package com.newsman.newsman.activities;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.manu_helpers.LoginMenuInflater;
import com.newsman.newsman.fragments.comment_fragment.delete_strategy.DeleteComment;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.picture_helpers.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.rest_connection.CompositeBuilder;
import com.newsman.newsman.rest_connection.UpdateBuilder;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.auxiliary.HistoryList.HistoryList;
import com.newsman.newsman.auxiliary.HistoryList.HistoryObject;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.db_entities.SimpleNews;
import com.newsman.newsman.fragments.comment_fragment.CommentsFragment;
import com.newsman.newsman.fragments.CreateNewsFragment;
import com.newsman.newsman.fragments.PicturesFragment;

import java.util.List;

public class UpdateNewsActivity extends AppCompatActivity {

    private int newsId;
    private CreateNewsFragment createNewsFragment;
    private PicturesFragment picturesFragment;
    private CommentsFragment commentsFragment;


    private News news;
    private HistoryList<Picture> pictureHistoryList;
    private HistoryList<Comment> commentHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        // TODO verovatno je najbolje da se posalje vest umesto id, ali za sad nek ide iz db

        BackArrowHelper.displayBackArrow(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_BUNDLE_KEY);
            setFragments();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return BackArrowHelper.backArrowClicked(this);
            case R.id.action_save_news:
                sendUpdateRequest();
                return true;
            default:
                if(LoginMenuInflater.handleOnMenuItemClick(item, getApplicationContext())) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        LoginMenuInflater.inflateLogin(inflater,menu);
        inflater.inflate(R.menu.save_news_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && (requestCode == Constant.REQUEST_LOAD_IMAGE
                || requestCode == Constant.REQUEST_IMAGE_CAPTURE)) {
            createNewsFragment.onActivityResult(requestCode, resultCode, data);
        } else if((resultCode == RESULT_OK && requestCode == Constant.PICTURE_REQUEST_CODE)){
            picturesFragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setFragments() {
        news = AppDatabase.getInstance(this).newsDao().getNewsByIdNonLive(newsId);
        createNewsFragment = CreateNewsFragment
                .newInstance(SimpleNews.getSimpleNews(news, this));
        List<Picture> pictures = AppDatabase.getInstance(this).pictureDao()
                .getPicturesForNewsNonLive(newsId);
        BitmapCache.getInstance().loadPicturesInCache(this, pictures);
        pictureHistoryList = new HistoryList<>(PictureLoader.loadPictureListData(this, pictures));
        picturesFragment = PicturesFragment.newInstance(newsId, pictureHistoryList, false);
        List<Comment> commentList = AppDatabase.getInstance(this).commentDao()
                .getCommentsForNewsNonLive(newsId);
        commentHistoryList = new HistoryList<>(commentList);
        commentsFragment = CommentsFragment.newInstance(newsId, commentHistoryList, new DeleteComment(), false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.update_news_news_frame, createNewsFragment)
                .add(R.id.update_news_pictures_frame, picturesFragment)
                .add(R.id.update_news_comments_frame, commentsFragment)
                .commit();
    }

    private SimpleNews getNewsData() {
        return createNewsFragment.getNews();
    }

    private void sendUpdateRequest() {
        List<HistoryObject<Picture>> pictureHistory = pictureHistoryList.getHistory();
        List<HistoryObject<Comment>> commentHistory = commentHistoryList.getHistory();
        UpdateBuilder ub = new CompositeBuilder(this);
        if(createNewsFragment.getUpdateStatus()) {
            ub.createNews(getNewsData());
        }
        for(HistoryObject<Picture> h: pictureHistory){
            switch (h.getOp()){
                case INSERT:
                    ub.addPicture(h.getObject());
                    break;
                case UPDATE:
                    ub.updatePicture(h.getObject());
                    break;
                case DELETE:
                    ub.deletePicture(h.getObject());
                    break;
            }
        }
        for(HistoryObject<Comment> h: commentHistory) {
            switch (h.getOp()) {
                case INSERT:
                    ub.addComment(h.getObject());
                    break;
                case DELETE:
                    ub.deleteComment(h.getObject());
                    break;
                case UPDATE:
                    break;
            }
        }
        AppExecutors.getInstance().getNetworkIO().execute(ub.getResult());
        finish();
    }
}
