package com.newsman.newsman.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Get;
import com.newsman.newsman.rest_connection.ConnectionStrategy.OutStreamConn;
import com.newsman.newsman.rest_connection.ReadJson.ReadComposite;
import com.newsman.newsman.rest_connection.ReadJson.ReadNews;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.thread_management.SubscriptionService;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button mLoadNewsButton;
    private Button mLogin;
    private Button mCreateAccount;
    private ImageView mImageView;
    private Button mRestPictureButton, mTestUpdate;
    private String KEY = "key";
    private AppDatabase mDB = null;
    private Button mImageSave;
    private Button startServiceBtn, sendServiceMsgBtn;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int PICTURE_ID = 20000;
    private String testRoute = Constant.PICTURE_ROUTE + "test";

    private static Handler uiHandler;
    static {
        uiHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        uiHandler.post(runnable);
    }

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
//                AppExecutors.getInstance().getMQConsumer().execute(new MQClient(Constant.getIpAddress(), mContext));
                Intent startMQClient = new Intent(mContext, SubscriptionService.class);
                startMQClient.setAction(SubscriptionService.START);
                startService(startMQClient);

                Intent intent = new Intent(mContext, NewsListActivity.class);
                startActivity(intent);
            }
        });
        mLoadNewsButton = findViewById(R.id.load_news_button);
        mImageView = findViewById(R.id.iv_picture);
        mCreateAccount = findViewById(R.id.create_account_activity);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        mLogin = findViewById(R.id.login_activity_btn);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
                new RestConnector(new Get(mContext, new ReadComposite(new ReadNews())),
                        Constant.NEWS_ROUTE).execute();
//                new RestConnector(new InStreamConn(mContext, PICTURE_ID), testRoute).execute();
            }
        });
        startServiceBtn = findViewById(R.id.start_service_btn);
        sendServiceMsgBtn = findViewById(R.id.send_service_msg);
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
        sendServiceMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void startService(){
        Intent intent = new Intent(this, SubscriptionService.class);
        intent.setAction("Action");
        startService(intent);
    }

    private void sendMessage() {
        Intent intent = new Intent(this, SubscriptionService.class);
        intent.setAction("Message");
        startService(intent);
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity3.class);
        startActivity(intent);
    }

    private void createAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bmp = PictureLoader.getResultingBitmap(requestCode, resultCode, data, this);
        savePicture(bmp);
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap bmp = (Bitmap) extras.get("data");
//            Picture picture = generateTestPicture(bmp);
//            new RestConnector(new put(new WritePicture(picture)), Constant.PICTURE_ROUTE).execute();
//        }
    }

    private Picture generateTestPicture(Bitmap bmp) {
        Picture p = new Picture();
        p.setId(Constant.INVALID_PICTURE_ID);
        p.setName(Integer.toString((int)new Date().getTime())+".PNG");
        p.setDescription("Nova fotka");
        p.setBelongsToNewsId(6);
        p.setPictureData(bmp);
        return p;
    }

    private void getPictureRest() {

        Bitmap bmp = PictureLoader.loadPictureData(this, 37);
        mImageView.setImageBitmap(bmp);
        new RestConnector(new OutStreamConn(bmp), testRoute).execute();
    }

    private void testPictureSave(){
//        PictureLoader.loadPictureFromGallery(this);
        Bitmap bmp = PictureLoader.loadPictureData(this, PICTURE_ID);
        mImageView.setImageBitmap(bmp);
    }

    private void savePicture(Bitmap bmp) {
        int id = 100000;
        PictureLoader.savePictureData(this, id, bmp);
        Bitmap res = PictureLoader.loadPictureData(this, id);
        mImageView.setImageBitmap(res);
    }
}
