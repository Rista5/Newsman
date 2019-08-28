package com.newsman.newsman.activities;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.auxiliary.PopUpMenuController;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.database.UserDao;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.fragments.comment_fragment.delete_strategy.HideDelete;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.picture_management.BitmapObserver;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.R;
import com.newsman.newsman.fragments.comment_fragment.CommentsFragment;
import com.newsman.newsman.fragments.PicturesFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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

        //TODO mozda treba da se skloni
        BackArrowHelper.displayBackArrow(this);

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
                if(news.getBackgroundId() != Constant.INVALID_PICTURE_ID){
                    BitmapObserver observer = new BitmapObserver(background);
                    Observable observable = BitmapCache.getInstance().getBitmap(getApplicationContext(), news.getBackgroundId(), news.getId());
                    observable.addObserver(observer);
                }
//                    background.setImageBitmap(PictureLoader.loadPictureData(mContext, news.getBackgroundId()));
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
                    commentsFragment.setCommentList(commentList);
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
                if(pictures == null)return;
//                BitmapCache.getInstance().loadPicturesInCache(getApplicationContext(), pictures);
                picturesFragment.setPictureList(PictureLoader.loadPictureListData(mContext, pictures));
            }
        });
    }

    private void inflateCommentsFragment() {
        commentsFragment = CommentsFragment.newInstance(newsId,
                new ArrayList<Comment>(), new HideDelete());
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
