package com.newsman.newsman.rest_connection.rest_connectors;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.picture_helpers.PictureData;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.model.dtos.CommentDTO;
import com.newsman.newsman.model.dtos.NewsDTO;
import com.newsman.newsman.model.dtos.PictureDTO;
import com.newsman.newsman.model.dtos.SimpleNewsDTO;
import com.newsman.newsman.rest_connection.retrofit_services.NewsService;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.db_entities.SimpleNews;
import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.thread_management.SubscriptionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class NewsConnector {
    public static Runnable loadAllSimpleNews(Context context){
        return ()->{
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<List<SimpleNewsDTO>> newsListCall = retrofit.create(NewsService.class).getAllSimpleNews();
            try{
                Response<List<SimpleNewsDTO>> response = newsListCall.execute();
                if(response.body() == null) return;
                for(SimpleNewsDTO news: response.body()){
                    News retrivedNews = new News();
                    SimpleNews.populateNews(retrivedNews, news.getSimpleNews());
                    AppDatabase.getInstance(context).newsDao().insertNews(retrivedNews);
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public static Runnable loadAllNews(Context context){
        return ()->{
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<List<NewsDTO>> newsListCall = retrofit.create(NewsService.class).getAllNews();
            try{
                Response<List<NewsDTO>> response = newsListCall.execute();
                if(response.body() == null) return;
                int[] subs = null;
                if(response.body().size() >0 ) {
                    subs = AppDatabase.getInstance(context).newsDao().getSubscribedNewsIds();
                    AppDatabase.getInstance(context).commentDao().deleteAllComments();
                    AppDatabase.getInstance(context).pictureDao().deleteAllPictures();
                    AppDatabase.getInstance(context).newsDao().deleteAllNews();
                }
                for(NewsDTO news: response.body()){
                    AppDatabase.getInstance(context).newsDao().insertNews(NewsDTO.getNews(news));
                    for(CommentDTO comment: news.getComments()) {
                        AppDatabase.getInstance(context).commentDao().insertComment(CommentDTO.getComment(comment));
                    }
                    for(PictureDTO picture: news.getPictures()) {
                        AppDatabase.getInstance(context).pictureDao().insertPicture(PictureDTO.getPicture(picture));
                    }
                }
                if(subs != null && subs.length>0) {
                    for(int i: subs){
                        AppDatabase.getInstance(context).newsDao().subscribeToNews(i);
                    }
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            SubscriptionService.startClient(context);
        };
    }

    public static Runnable loadNewsById(Context context,final int Id){
        return  () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<NewsDTO> getCall = retrofit.create(NewsService.class).getNewsById(Id);
            try{
                Response<NewsDTO> response = getCall.execute();
                if(response.body()!= null){
                    NewsDTO news = response.body();
                    AppDatabase.getInstance(context).newsDao().updateNews(NewsDTO.getNews(news));
                    for(CommentDTO comment: news.getComments()) {
                        AppDatabase.getInstance(context).commentDao().updateComment(CommentDTO.getComment(comment));
                    }
                    for(PictureDTO picture: news.getPictures()) {
                        AppDatabase.getInstance(context).pictureDao().updatePicture(PictureDTO.getPicture(picture));
                    }
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public static Runnable saveNews(Context context, final int userId, final News news, final Bitmap background){
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            List<Picture> pictures = news.getPictures();
            news.setPictures(new ArrayList<>());
            Call<NewsDTO> putCall = retrofit.create(NewsService.class).putNews(userId, new NewsDTO(news));
            try {
                Response<NewsDTO> response = putCall.execute();
                if(response.body() == null) return;
                News newsResult = NewsDTO.getNews(response.body());
                AppDatabase.getInstance(context).newsDao().insertNews(newsResult);
                AppExecutors.getInstance().getNetworkIO()
                        .execute(BitmapConnector.saveBitmap(newsResult.getId(), newsResult.getBackgroundId(), background));
                BitmapCache.getInstance().deleteBitmap(news.getBackgroundId());
                List<PictureData> data = getPictureData(context, newsResult.getId(), pictures);
                for(PictureData d: data) {
                    AppExecutors.getInstance().getNetworkIO()
                            .execute(PictureConnector.savePicture(context, d.getPicture(), d.getBitmap()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static List<PictureData> getPictureData(Context context, int newsId, List<Picture> pictureList) {
        List<PictureData> data = new ArrayList<>(pictureList.size());
        BitmapCache cache = BitmapCache.getInstance();
        for(Picture p: pictureList) {
            Bitmap bmp = cache.getBitmapObservable(context, p.getId(), p.getBelongsToNewsId()).getBitmap();
            p.setBelongsToNewsId(newsId);
            data.add(new PictureData(p, bmp));
        }
        return data;
    }

    public static Runnable saveNews(Context context, final int userId, final SimpleNews news){
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<SimpleNewsDTO> postCall = retrofit
                    .create(NewsService.class)
                    .postNews(userId,new SimpleNewsDTO(news));
            try {
                Response<SimpleNewsDTO> response = postCall.execute();
                SimpleNewsDTO dto = response.body();
                if(dto != null){
                    SimpleNews simpleNews = SimpleNewsDTO.getSimpleNews(dto);
                    News updatedNews = new News();
                    SimpleNews.populateNews(updatedNews, simpleNews);
                    AppDatabase.getInstance(context).newsDao().updateNews(updatedNews);
                    if(news.getBackgroundId() != Constant.INVALID_PICTURE_ID) {
                        AppExecutors.getInstance().getNetworkIO().execute(BitmapConnector
                                .saveBitmap(simpleNews.getId(), simpleNews.getBackgroundId(), news.getBackgroundPicture()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable deleteNews(Context context, final int newsId){
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<Boolean> deleteNewsCall = retrofit
                    .create(NewsService.class)
                    .deleteNews(newsId);
            try {
                Response<Boolean> response = deleteNewsCall.execute();
                Boolean result = response.body();
                if(result != null){
                    AppDatabase.getInstance(context).newsDao().deleteNewsById(newsId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
