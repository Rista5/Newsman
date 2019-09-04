package com.newsman.newsman.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.auxiliary.PictureData;
import com.newsman.newsman.auxiliary.TempObjectGenerator;
import com.newsman.newsman.new_rest.NewsConnector;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.rest_connection.ConnectionStrategy.PutAndGet;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.rest_connection.WriteJson.WriteNews;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;
import com.newsman.newsman.fragments.CreateNewsFragment;
import com.newsman.newsman.fragments.PicturesFragment;
import com.newsman.newsman.thread_management.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class CreateNewsActivity extends AppCompatActivity {

    private CreateNewsFragment createNewsFragment;
    private PicturesFragment picturesFragment;

    private Bitmap backgroundPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);

        setFragments();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_news_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return BackArrowHelper.backArrowClicked(this);
            case R.id.action_save_news:
                postNews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postNews() {
        News news = createNews();
        BitmapCache.getInstance().setBitmap(news.getBackgroundId(),news.getId(),backgroundPic);
        if(!checkValidNews(news)) {
            displayToast();
        } else {
            AppExecutors.getInstance().getNetworkIO()
                    .execute(NewsConnector.saveNews(getApplicationContext(), LoginState.getInstance().getUserId(), news, backgroundPic));
            finish();
        }
    }

    private void setFragments() {
        SimpleNews news = TempObjectGenerator.genInvalidSimpleNews();
        createNewsFragment = CreateNewsFragment.newInstance(news);
        picturesFragment = PicturesFragment.newInstance(Constant.INVALID_NEWS_ID, new ArrayList<Picture>(), false);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.create_news_news_frame, createNewsFragment)
                .add(R.id.create_news_pictures_frame, picturesFragment)
                .commit();
    }

    private boolean checkValidNews(News news) {
        return !news.getTitle().equals("") && !news.getContent().equals("");
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
