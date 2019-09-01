package com.newsman.newsman.new_rest;

import android.content.Context;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.new_rest.dtos.NewsDTO;
import com.newsman.newsman.new_rest.dtos.SimpleNewsDTO;
import com.newsman.newsman.new_rest.retrofit_services.NewsService;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.SimpleNews;

import java.io.IOException;
import java.util.List;

import retrofit2.*;

public class NewsConnector {
    public Runnable loadAllSimpleNews(Context context){
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

    public Runnable loadAllNews(Context context){
        return ()->{
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<List<NewsDTO>> newsListCall = retrofit.create(NewsService.class).getAllNews();
            try{
                Response<List<NewsDTO>> response = newsListCall.execute();
                if(response.body() == null) return;
                for(NewsDTO news: response.body()){
                    AppDatabase.getInstance(context).newsDao().insertNews(NewsDTO.getNews(news));
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public Runnable loadNewsById(Context context,final int Id){
        return  () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<NewsDTO> getCall = retrofit.create(NewsService.class).getNewsById(Id);
            try{
                Response<NewsDTO> response = getCall.execute();
                if(response.body()!= null){
                    AppDatabase.getInstance(context).newsDao().insertNews(NewsDTO.getNews(response.body()));
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public Runnable saveNews(Context context,final int userId, final News news){
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            Call<NewsDTO> putCall = retrofit.create(NewsService.class).putNews(userId,new NewsDTO(news));
            try {
                Response<NewsDTO> response = putCall.execute();
                if(response.body() != null)
                    AppDatabase.getInstance(context).newsDao().insertNews(NewsDTO.getNews(response.body()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public Runnable saveNews(Context context, final int userId, final SimpleNews news){
        return () -> {
          Retrofit retrofit = RetrofitFactory.createInstance();
          Call<SimpleNewsDTO> postCall = retrofit
                  .create(NewsService.class)
                  .postNews(userId,new SimpleNewsDTO(news));
            try {
                Response<SimpleNewsDTO> response = postCall.execute();
                SimpleNewsDTO dto = response.body();
                if(dto!=null){
                    News updatedNews = new News();
                    SimpleNews simpleNews = dto.getSimpleNews();
                    SimpleNews.populateNews(updatedNews,dto.getSimpleNews());
                    AppDatabase.getInstance(context).newsDao().updateNews(updatedNews);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public Runnable deleteNews(Context context, final int newsId){
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
