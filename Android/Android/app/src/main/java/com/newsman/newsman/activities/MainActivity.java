package com.newsman.newsman.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.newsman.newsman.AppExecutors;
import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.REST.ConnectionStrategy.Get;
import com.newsman.newsman.REST.ReadJson.ReadComposite;
import com.newsman.newsman.REST.ReadJson.ReadNews;
import com.newsman.newsman.REST.ReadJson.ReadPicture;
import com.newsman.newsman.REST.RestConnector;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.message_queue.MQClient;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button mLoadNewsButton;
    private Button mTestDbButton;
    private Button mTestPicture;
    private ImageView mImageView;
    private Button mRestPictureButton, mTestUpdate;
    private String KEY = "key";
    private AppDatabase mDB = null;
    private Button mImageSave;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mDB = AppDatabase.getInstance(getApplicationContext());

        Button button = findViewById(R.id.lounch_news_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new GetNewsFromRest().Get(mContext);
                new RestConnector(new Get(mContext, new ReadComposite(new ReadNews())),
                        Constant.NEWS_ROUTE).execute();
                AppExecutors.getInstance().getMQHandler().execute(new MQClient(Constant.getIpAddress(), mContext));
                Intent intent = new Intent(mContext, NewsListActivity.class);
                startActivity(intent);
            }
        });
        mLoadNewsButton = findViewById(R.id.load_news_button);
        mImageView = findViewById(R.id.iv_picture);
        mTestPicture = findViewById(R.id.test_picture);
        mTestPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        mRestPictureButton = findViewById(R.id.rest_picture);
        mRestPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPictureRest();
            }
        });
        mTestUpdate = findViewById(R.id.test_update_activity);
        mImageSave = findViewById(R.id.test_save_img);
        mImageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPictureSave();
            }
        });
        mTestUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.NEWS_BUNDLE_KEY, 4);
                Intent intent = new Intent(mContext, UpdateNewsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        //TODO fix this handler warrning
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                final List<News> news = (List<News>)bundle.getSerializable(KEY);
                if (news != null) {
                    for(News n: news) {
                        Log.w("TAG", n.getTitle());

                    }
                    AppExecutors.getInstance().getDatabaseIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            for(News n: news) {
                                mDB.newsDao().updateNews(n);
                            }
                            Log.d("DB", "news successfully saved");
                        }
                    });
                }
            }
        };

        mLoadNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new GetNewsFromRest(IP_ADDRESSE, KEY).Get(handler);
            }
        });

        mTestDbButton = findViewById(R.id.load_news_from_db);
        mTestDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logNews();
            }
        });
    }

    private void logNews() {
        LiveData<List<News>> liveNews = mDB.newsDao().getAllNews();
        liveNews.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                Log.d("THIS", this.toString());
                for(News n:news) {
                    Log.d("DB", "news id: "+n.getId());
                }
            }
        });
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bmp = PictureLoader.getResultingBitmap(requestCode, resultCode, data, this);
        savePicture(bmp);
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap bmp = (Bitmap) extras.get("data");
//            Picture picture = generateTestPicture(bmp);
//            new RestConnector(new Put(new WritePicture(picture)), Constant.PICTURE_ROUTE).execute();
//        }
    }

    private Picture generateTestPicture(Bitmap bmp) {
        Picture p = new Picture();
        p.setId(-1);
        p.setName(Integer.toString((int)new Date().getTime())+".PNG");
        p.setDescription("Nova fotka");
        p.setBelongsToNewsId(6);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] picData = stream.toByteArray();
        p.setPictureData(picData);
        return p;
    }

    private void getPictureRest() {
        new RestConnector(new Get(this, new ReadPicture()), Constant.PICTURE_ROUTE+10);
        LiveData<Picture> livePicture = AppDatabase.getInstance(this).pictureDao().getPicture(10);
        livePicture.observe(this, new Observer<Picture>() {
            @Override
            public void onChanged(@Nullable Picture picture) {
                if(picture!=null) {
                    Bitmap bmp = PictureConverter.getBitmap(picture.getPictureData());
                    mImageView.setImageBitmap(bmp);
                }
            }
        });
    }

    private void testPictureSave(){
        PictureLoader.loadPictureFromGallery(this);
    }

    private void savePicture(Bitmap bmp) {
        int id = 100000;
        PictureLoader.savePictureData(this, id, bmp);
        Bitmap res = PictureLoader.loadPictureData(this, id);
        mImageView.setImageBitmap(res);
    }
}