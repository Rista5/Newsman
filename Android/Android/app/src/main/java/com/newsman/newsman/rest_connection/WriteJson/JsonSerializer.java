package com.newsman.newsman.rest_connection.WriteJson;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.JsonWriter;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureConverter;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;
import com.newsman.newsman.server_entities.User;
import com.newsman.newsman.server_entities.UserWithPassword;

import java.io.IOException;

class JsonSerializer {

    static void writePicture(JsonWriter jsonWriter, Picture picture) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(picture.getId());
        jsonWriter.name("Name").value(picture.getName());
        jsonWriter.name("Description").value(picture.getDescription());
        jsonWriter.name("BelongsToNewsId").value(picture.getBelongsToNewsId());
//        jsonWriter.name("PictureData")
//                .value(Base64.encodeToString(PictureConverter
//                        .getBitmapBytes(picture.getPictureData()), Base64.DEFAULT));
        jsonWriter.name("PictureData").value(picture.getId());
        jsonWriter.endObject();
    }

    static void writeComment(JsonWriter jsonWriter, Comment comment) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(comment.getId());
        jsonWriter.name("Content").value(comment.getContent());
        jsonWriter.name("BelongsToNewsId").value(comment.getBelongsToNewsId());
        jsonWriter.name("CreatedBy");
        writeUser(jsonWriter, comment.getCreatedById(), comment.getUsername());
        jsonWriter.name("PostDate").value(comment.getPostDate().toString());
        jsonWriter.endObject();
    }

    static void writeNews(JsonWriter jsonWriter, News news, Bitmap background) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(news.getId());
        jsonWriter.name("Title").value(news.getTitle());
        jsonWriter.name("Content").value(news.getContent());
        jsonWriter.name("LasModified").value(news.getLastModified().toString());
        jsonWriter.name("LastModifiedUser");
        writeUser(jsonWriter, news.getModifierId(), news.getModifierUsername());
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
        jsonWriter.name("LastModifiedUser");
        writeUser(jsonWriter, news.getModifierId(), news.getModifierUsername());
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

    static void writeUser(JsonWriter jsonWriter, int userId, String username) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(userId);
        jsonWriter.name("Username").value(username);
        jsonWriter.endObject();
    }

    static void writeUserPass(JsonWriter jsonWriter, UserWithPassword user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(Constant.INVALID_USER_ID);
        jsonWriter.name("Username").value(user.getUsername());
        jsonWriter.name("Password").value(user.getPassword());
        jsonWriter.endObject();
    }
}
