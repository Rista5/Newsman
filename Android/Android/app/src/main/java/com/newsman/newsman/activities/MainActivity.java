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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.new_rest.CommentDTO;
import com.newsman.newsman.new_rest.CommentService;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
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
                new RestConnector(new Get(mContext, new ReadComposite(new ReadNews())),
                        Constant.NEWS_ROUTE).execute();
//                new RestConnector(new InStreamConn(mContext, PICTURE_ID), testRoute).execute();
            }
        });

        mTestDbButton = findViewById(R.id.load_news_from_db);
        mTestDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logNews();
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
        new Thread(new MQClient(Constant.getIpAddress(), this, subs), "TEST_THREAD");
    }

    private void testPictureSave(){
//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
//        String commentJson = gson.toJson(genNews());
//        Log.d("GSON josn", commentJson);
//        News n = gson.fromJson(commentJson, News.class);
//        Log.d("Comment", "id= "+c.getId()+", content= "+c.getContent()+ ",username= "+c.getUsername()+", postDate= "+c.getPostDate().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + Constant.getIpAddress() + ":52752/api/")
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();
        CommentService service = retrofit.create(CommentService.class);
        Call<List<CommentDTO>> comments = service.getAllComments();
        new Thread(() -> {
            Response<List<CommentDTO>> res = null;
            try {
                res = comments.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<CommentDTO> body = res.body();
        }).start();

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
        int id = 100000;
        PictureLoader.savePictureData(this, id, bmp);
        Bitmap res = PictureLoader.loadPictureData(this, id);
        mImageView.setImageBitmap(res);
    }

    public static Handler UIHandler;
    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable run){
        UIHandler.post(run);
    }
}
