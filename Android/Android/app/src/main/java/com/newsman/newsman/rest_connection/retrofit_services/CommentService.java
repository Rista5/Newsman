package com.newsman.newsman.rest_connection.retrofit_services;

import com.newsman.newsman.model.dtos.CommentDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentService {
    @GET("Comment")
    Call<List<CommentDTO>> getAllComments();

    @GET("Comment/{commentId}")
    Call<CommentDTO> getComment(@Path("commentId") int commentId);

    @GET("Comment/FromNews/{newsId}")
    Call<List<CommentDTO>> getCommentsForNews(@Path("newsId") int newsId);

    @POST("Comment/{userId}")
    Call<CommentDTO> createComment(@Path("userId") int userId, @Query("content") String content);

    @PUT("Comment")
    Call<CommentDTO> createComment(@Body CommentDTO comment);

    @DELETE("Comment/{commentId}")
    Call<Boolean> deleteComment(@Path("commentId") int commentId);
}
