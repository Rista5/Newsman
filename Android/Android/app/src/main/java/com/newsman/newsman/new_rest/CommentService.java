package com.newsman.newsman.new_rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentService {
    @GET("Comment")
    Call<List<CommentDTO>> getAllComments();

    @GET("Comment/{commentId}")
    Call<CommentDTO> getComment(@Path("commentId") int commentId);
}
