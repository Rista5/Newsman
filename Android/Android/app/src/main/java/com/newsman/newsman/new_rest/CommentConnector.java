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
    public Runnable saveComment(final Context context, final Comment comment){
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();;
            CommentDTO commentDTO = new CommentDTO(comment);
            CommentService service = retrofit.create(CommentService.class);
            Call<CommentDTO> commentCall = service.createComment(commentDTO);
            try {
                Response<CommentDTO> response = commentCall.execute();
                if(response.body() == null) return;
                AppDatabase.getInstance(context).commentDao().insertComment(CommentDTO.getComment(response.body()));
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public Runnable loadComment(final Context context, final int newsId){
        return () -> {
          Retrofit retrofit = RetrofitFactory.createInstance();;
          Call<List<CommentDTO>> commentCall = retrofit.create(CommentService.class).getCommentsForNews(newsId);
          try{
              Response<List<CommentDTO>> response = commentCall.execute();
              List<CommentDTO> comments = response.body();
              if(comments == null) return;
              for(CommentDTO comment: comments){
                  AppDatabase.getInstance(context).commentDao().insertComment(CommentDTO.getComment(comment));
              }
          }
          catch (IOException ex){
              ex.printStackTrace();
          }
        };
    }

    public Runnable deleteComment(final Context context, final int commentId){
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();;
            Call<Boolean> deleteCall = retrofit.create(CommentService.class).deleteComment(commentId);
            try{
                Response<Boolean> response = deleteCall.execute();
                if(response.body() != null)
                    AppDatabase.getInstance(context).commentDao().deleteCommentById(commentId);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }
}
