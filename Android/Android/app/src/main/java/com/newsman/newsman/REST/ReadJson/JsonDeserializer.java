package com.newsman.newsman.REST.ReadJson;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.JsonReader;
import android.util.JsonToken;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.DateGetter;
import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;
import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class JsonDeserializer {

    static News readNews(JsonReader jsonReader)throws IOException {
        int id = 0;
        String title = "";
        String content="";
        List<Comment> comments = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String lastModified = "";
        Picture back =  null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String name = jsonReader.nextName();
            switch (name) {
                case "Id":
                    id = jsonReader.nextInt();
                    break;
                case "Title":
                    title = jsonReader.nextString();
                    break;
                case "Content":
                    content = jsonReader.nextString();
                    break;
                case "Comments":
                    comments = readCommentArray(jsonReader);
                    break;
                case "LasModified":
                    lastModified = jsonReader.nextString();
                    break;
                case "Pictures":
                    pictures = readPictureArray(jsonReader);
                    break;
                case "BackgroundPicture":
                    if (!jsonReader.peek().equals(JsonToken.NULL))
                        back = readPicture(jsonReader);
                    else
                        jsonReader.skipValue();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        int backId = Constant.INVALID_PICTURE_ID;
        if(back != null) {
            pictures.add(back);
            backId = back.getId();
        }
        return new News(id, title, content, comments, DateGetter.parseDate(lastModified), pictures, backId);
    }

    static SimpleNews readSimpleNews(JsonReader jsonReader) throws IOException {
        int id = Constant.INVALID_NEWS_ID;
        String title = "";
        String content="";
        String lastModified = "";
        Picture back = new Picture(Constant.INVALID_PICTURE_ID, null,null,Constant.INVALID_NEWS_ID, null);
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case "Id":
                    id = jsonReader.nextInt();
                    break;
                case "Title":
                    title = jsonReader.nextString();
                    break;
                case "Content":
                    content = jsonReader.nextString();
                    break;
                case "LasModified":
                    lastModified = jsonReader.nextString();
                    break;
                case "BackgroundPicture":
                    if (!jsonReader.peek().equals(JsonToken.NULL))
                        back = readPicture(jsonReader);
                    else
                        jsonReader.skipValue();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        return new SimpleNews(id, title, content, DateGetter.parseDate(lastModified),
                back.getPictureData(), back.getBelongsToNewsId());
    }

    public static List<Comment> readCommentArray(JsonReader jsonReader)throws IOException {
        List<Comment> comments = new ArrayList<>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            comments.add(readComment(jsonReader));
        }
        jsonReader.endArray();
        return comments;
    }

    static Comment readComment(JsonReader jsonReader)throws IOException{
        int id = 0;
        String content ="";
        User user = null;
        int newsId = 0;
        String postDate = null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String name = jsonReader.nextName();
            switch (name) {
                case "Id":
                    id = jsonReader.nextInt();
                    break;
                case "CreatedBy":
                    user = readUser(jsonReader);
                    break;
                case "Content":
                    content = jsonReader.nextString();
                    break;
                case "BelongsToNewsId":
                    newsId = jsonReader.nextInt();
                    break;
                case "PostDate":
                    postDate = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        Comment comment = new Comment(id,content,user.getId(),newsId, postDate);
        comment.setCreatedBy(user);
        return comment;
    }

    private static User readUser(JsonReader reader) throws IOException {
        String username = null;
        int id =0;
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("Id")){
                id = reader.nextInt();
            } else if (name.equals("Username")){
                username = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(id, username);
    }


    private static List<Picture> readPictureArray(JsonReader jsonReader) throws IOException {
        List<Picture> pictures = new ArrayList<>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()) {
            pictures.add(readPicture(jsonReader));
        }
        jsonReader.endArray();
        return pictures;
    }

    static Picture readPicture(JsonReader jsonReader) throws IOException {
        int id = Constant.INVALID_PICTURE_ID;
        String pictName = "";
        String description = "";
        int belongsToNews = Constant.INVALID_NEWS_ID;
        byte[] pictureData = null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch(name) {
                case "Id":
                    id = jsonReader.nextInt();
                    break;
                case "Name":
                    pictName = jsonReader.nextString();
                    break;
                case "Description":
                    description = jsonReader.nextString();
                    break;
                case "BelongsToNewsId":
                    belongsToNews = jsonReader.nextInt();
                    break;
                case "PictureData":
                    if(!jsonReader.peek().equals(JsonToken.NULL)) {
                        String stringData = jsonReader.nextString();
                        pictureData = Base64.decode(stringData, Base64.DEFAULT);
                    } else {
                        jsonReader.skipValue();
                    }
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        Bitmap bmp = null;
        try{
            bmp = PictureConverter.getBitmap(pictureData);
        }catch (NullPointerException e ){
            e.printStackTrace();
        }
        return new Picture(id, pictName, description, belongsToNews, bmp);
    }
}
