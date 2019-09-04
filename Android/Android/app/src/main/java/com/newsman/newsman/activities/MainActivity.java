package com.newsman.newsman.activities;

import android.annotation.SuppressLint;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.new_rest.BitmapConnector;
import com.newsman.newsman.new_rest.NewsConnector;
import com.newsman.newsman.new_rest.PictureConnector;
import com.newsman.newsman.new_rest.dtos.PictureDTO;
import com.newsman.newsman.new_rest.retrofit_services.PictureService;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Get;
import com.newsman.newsman.rest_connection.ReadJson.ReadComposite;
import com.newsman.newsman.rest_connection.ReadJson.ReadNews;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.thread_management.SubscriptionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private Button startServiceBtn, sendServiceMsgBtn;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int PICTURE_ID = 20000;
    private String testRoute = Constant.PICTURE_ROUTE + "test";

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
        mTestPicture = findViewById(R.id.test_create_account);
        mTestPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        mTestDbButton = findViewById(R.id.test_login);
        mTestDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logNews();
            }
        });
        mRestPictureButton = findViewById(R.id.rest_picture);
        mRestPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMq();
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
                AppExecutors.getInstance().getNetworkIO()
                        .execute(NewsConnector.loadAllNews(getApplicationContext()));
//                new RestConnector(new Get(mContext, new ReadComposite(new ReadNews())),
//                        Constant.NEWS_ROUTE).execute();
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

    private void logNews() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

    private void testMq() {
        int[] subs = new int[100];
        for(int i=0; i< 100; i++)
            subs[i] = i+1;
        AppExecutors.getInstance().getNetworkIO().execute(
                new MQClient(Constant.getIpAddress(), this, subs));
    }

    private void testPictureSave(){
//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
//        String commentJson = gson.toJson(genNews());
//        Log.d("GSON josn", commentJson);
//        News n = gson.fromJson(commentJson, News.class);
//        Log.d("Comment", "id= "+c.getId()+", content= "+c.getContent()+ ",username= "+c.getUsername()+", postDate= "+c.getPostDate().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.getBaseUrl())
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();
        PictureService service = retrofit.create(PictureService.class);
        Call<PictureDTO> call = service.getPicture(66);

        call.enqueue(new Callback<PictureDTO>() {

            @Override
            public void onResponse(Call<PictureDTO> call, Response<PictureDTO> response) {
                PictureDTO pic = response.body();
            }

            @Override
            public void onFailure(Call<PictureDTO> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }

    private News genNews() {
        News n= new News();
        n.setId(0);
        n.setTitle("Title");
        List<Comment> coms = new ArrayList<>();
        coms.add(genComment());
        coms.add(genComment());
        n.setComments(coms);
        n.setContent("COTNENT");
        return n;
    }

    private Comment genComment() {
        Comment c= new Comment();
        c.setId(0);
        c.setCreatedById(3);
        c.setContent("Contentntntntnt");
        c.setPostDate(new Date());
        c.setUsername("Neki user");
        return c;
    }

    private void savePicture(Bitmap bmp) {
        int newsId = 40;
        int pictureId = 43;
        Picture p = new Picture(0, "test", "desc", 43, null);
        AppExecutors.getInstance().getNetworkIO().execute( PictureConnector.savePicture(this, p, bmp));
        mImageView.setImageBitmap(bmp);

    }

    public static Handler UIHandler;
    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable run){
        UIHandler.post(run);
    }
}
