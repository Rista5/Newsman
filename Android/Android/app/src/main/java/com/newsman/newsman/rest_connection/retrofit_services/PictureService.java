package com.newsman.newsman.rest_connection.retrofit_services;

import com.newsman.newsman.model.dtos.PictureDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PictureService {

    @GET("Picture/FromNews")
    Call<List<PictureDTO>> getPicturesForNews(@Query("newsID") int newsId);

    @GET("Picture/{id}")
    Call<PictureDTO> getPicture(@Path("id") int pictureId);

    @PUT("Picture")
    Call<PictureDTO> createPicture(@Body PictureDTO picture);

    @POST("Picture")
    Call<PictureDTO> updatePicture(@Body PictureDTO picture);

    @DELETE("Picture/{id}")
    Call<Boolean> deletePicture(@Path("id") int pictureId);
}
