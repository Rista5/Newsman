package com.newsman.newsman.new_rest.retrofit_services;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface BitmapService {

    @GET("picture/raw")
    Call<ResponseBody> getPictureData(@Query("picId") int pictureId, @Query("newsId") int newsId);

    @PUT("picture/raw")
    Call<ResponseBody> savePicture(@Query("picId") int pictureId, @Query("newsId") int newsId, @Body RequestBody bytes);

}
