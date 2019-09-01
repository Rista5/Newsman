package com.newsman.newsman.new_rest;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.new_rest.dtos.CommentDTO;
import com.newsman.newsman.new_rest.dtos.NewsDTO;
import com.newsman.newsman.new_rest.retrofit_services.CommentService;
import com.newsman.newsman.new_rest.retrofit_services.NewsService;
import com.newsman.newsman.server_entities.Comment;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentConnector {
    public Runnable saveComment(final Comment comment){
        return () -> {
            Retrofit retrofit;
            CommentDTO commentDTO = new CommentDTO(comment);
            CommentService service = retrofit.create(CommentService.class);
            Call<CommentDTO> commentCall = service.createComment(commentDTO);
            try {
                commentCall.execute();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public Runnable loadComment(Context context, final int newsId){
        return () -> {
          Retrofit retrofit;
          Call<List<CommentDTO>> commentCall = retrofit.create(CommentService.class).getCommentsForNews(newsId);
          try{
              Response<List<CommentDTO>> response = commentCall.execute();
              List<CommentDTO> comments = response.body();
              for(CommentDTO comment: comments){
                  AppDatabase.getInstance(context).commentDao().insertComment(comment.getComment());
              }
          }
          catch (IOException ex){

          }
        };
    }

    public Runnable deleteComment(Context context, final int commentId){
        return () -> {
            Retrofit retrofit;
            Call<Boolean> deleteCall = retrofit.create(CommentService.class).deleteComment(commentId);
            try{
                Response<Boolean> response = deleteCall.execute();
                if(response.body())
                    AppDatabase.getInstance(context).commentDao().deleteCommentById(commentId);
            }
            catch (IOException ex){

            }
        };
    }
}
