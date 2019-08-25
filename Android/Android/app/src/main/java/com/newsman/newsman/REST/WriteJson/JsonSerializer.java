package com.newsman.newsman.REST.WriteJson;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.JsonWriter;

import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;
import com.newsman.newsman.ServerEntities.User;
import com.newsman.newsman.ServerEntities.UserWithPassword;

import java.io.IOException;

class JsonSerializer {

    static void writePicture(JsonWriter jsonWriter, Picture picture) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(picture.getId());
        jsonWriter.name("Name").value(picture.getName());
        jsonWriter.name("Description").value(picture.getDescription());
        jsonWriter.name("BelongsToNewsId").value(picture.getBelongsToNewsId());
        jsonWriter.name("PictureData")
                .value(Base64.encodeToString(PictureConverter
                        .getBitmapBytes(picture.getPictureData()), Base64.DEFAULT));
        jsonWriter.endObject();
    }

    static void writeComment(JsonWriter jsonWriter, Comment comment) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(comment.getId());
        jsonWriter.name("Content").value(comment.getContent());
        jsonWriter.name("BelongsToNewsId").value(comment.getBelongsToNewsId());
        jsonWriter.name("CreatedBy").value(comment.getCreatedById());
        jsonWriter.name("PostDate").value(comment.getPostDate().toString());
        jsonWriter.endObject();
    }

    static void writeNews(JsonWriter jsonWriter, News news, Bitmap background) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(news.getId());
        jsonWriter.name("Title").value(news.getTitle());
        jsonWriter.name("Content").value(news.getContent());

        jsonWriter.name("BackgroundPicture");
        if(background != null) {
            Picture pic = new Picture(
                    news.getBackgroundId(),
                    "",
                    "",
                    news.getId(),
                    background
            );
            writePicture(jsonWriter, pic);
        } else {
            jsonWriter.nullValue();
        }

        jsonWriter.name("Comments");
        jsonWriter.beginArray();
        for(Comment c: news.getComments()) {
            writeComment(jsonWriter, c);
        }
        jsonWriter.endArray();

        jsonWriter.name("Pictures");
        jsonWriter.beginArray();
        for(Picture p: news.getPictures()) {
            writePicture(jsonWriter, p);
        }
        jsonWriter.endArray();

        jsonWriter.endObject();
    }

    static  void writeSimpleNews(JsonWriter jsonWriter, SimpleNews news) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(news.getId());
        jsonWriter.name("Title").value(news.getTitle());
        jsonWriter.name("Content").value(news.getContent());
        jsonWriter.name("BackgroundPicture");
        Picture back = new Picture(news.getBackgroundId(), "", "", news.getId(),
                news.getBackgroundPicture());
        writePicture(jsonWriter, back);
        jsonWriter.endObject();
    }

    static void writeUser(JsonWriter jsonWriter, User user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(user.getId());
        jsonWriter.name("Username").value(user.getUsername());
        jsonWriter.endObject();
    }

    static void writeUserPass(JsonWriter jsonWriter, UserWithPassword user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Username").value(user.getUsername());
        jsonWriter.name("Password").value(user.getPassword());
        jsonWriter.endObject();
    }
}
