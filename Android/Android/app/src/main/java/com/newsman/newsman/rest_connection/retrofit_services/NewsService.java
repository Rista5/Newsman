package com.newsman.newsman.rest_connection.retrofit_services;

import com.newsman.newsman.model.dtos.NewsDTO;
import com.newsman.newsman.model.dtos.SimpleNewsDTO;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.*;

public interface NewsService {

    @GET("News")
    Call<List<NewsDTO>> getAllNews();

    @GET("SimpleNews")
    Call<List<SimpleNewsDTO>> getAllSimpleNews();

    @GET("NewsModifiedByUser/{id}")
    Call<List<NewsDTO>> getNewsModifiedByUser(@Path("id") int id);

    @GET("News/{id}")
    Call<NewsDTO> getNewsById(@Path("id") int id);

    @PUT("News/{userId}")
    Call<NewsDTO> putNews(@Path("userId") int userId, @Body NewsDTO newsDTO);

    @POST("News/{userId}")
    Call<SimpleNewsDTO> postNews(@Path("userId") int userId, @Body SimpleNewsDTO newsDTO);

    @DELETE("News/{newsId}")
    Call<Boolean> deleteNews(@Path("newsId") int newsId);
}
