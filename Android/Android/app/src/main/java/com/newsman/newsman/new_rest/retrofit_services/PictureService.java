package com.newsman.newsman.new_rest.retrofit_services;

import com.newsman.newsman.new_rest.dtos.PictureDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PictureService {

    @GET("Picture/FromNews/{newsId}")
    Call<List<PictureDTO>> getPicturesForNews(@Path("newsId") int newsId);

    @GET("Picture/{pictureId}")
    Call<PictureDTO> getPicture(@Path("pictureId") int pictureId);

    @PUT("Picture")
    Call<PictureDTO> createPicture(@Body PictureDTO picture);

    @POST("Picture")
    Call<PictureDTO> updatePicture(@Body PictureDTO picture);

    @DELETE("Picture/{pictureId}")
    Call<Boolean> deletePicture(@Path("pictureId") int pictureId);
}
