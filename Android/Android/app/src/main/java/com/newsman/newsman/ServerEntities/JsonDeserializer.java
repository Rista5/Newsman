package com.newsman.newsman.ServerEntities;

import android.util.Base64;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

public class JsonDeserializer {

    public static News readNews(JsonReader jsonReader)throws IOException {
        int id = 0;
        String title = "";
        String content="";
        //TODO change arrayList to List
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<Picture> pictures = new ArrayList<>();
        String lastModified = "";
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String name = jsonReader.nextName();
            if(name.equals("Id")){
                id = jsonReader.nextInt();
            } else if(name.equals("Title")){
                title = jsonReader.nextString();
            } else if(name.equals("Content")){
                content = jsonReader.nextString();
            } else if(name.equals("Comments")){
                comments = readCommentArray(jsonReader);
            } else if(name.equals("LasModified")){
                lastModified = jsonReader.nextString();
            } else if(name.equals("Pictures")){
                pictures = readPictureArray(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return new News(id,title,content,comments,lastModified, pictures);
    }

    public static ArrayList<Comment> readCommentArray(JsonReader jsonReader)throws IOException {
        ArrayList<Comment> comments = new ArrayList<>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            comments.add(readComment(jsonReader));
        }
        jsonReader.endArray();
        return comments;
    }

    public static Comment readComment(JsonReader jsonReader)throws IOException{
        int id = 0;
        String content ="";
        User user = null;
        int newsId = 0;
        String postDate = null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String name = jsonReader.nextName();
            if(name.equals("Id")){
                id = jsonReader.nextInt();
            }else if(name.equals("CreatedBy")){
                user = readUser(jsonReader);
            } else if(name.equals("Content")){
                content = jsonReader.nextString();
            }else if(name.equals("BelongsToNewsId")){
                newsId = jsonReader.nextInt();
            }else if(name.equals("PostDate")){
                postDate = jsonReader.nextString();
            }else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        Comment comment = new Comment(id,content,user.getId(),newsId, postDate);
        comment.setCreatedBy(user);
        return comment;
    }

    public static User readUser(JsonReader reader) throws IOException {
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


    private static ArrayList<Picture> readPictureArray(JsonReader jsonReader) throws IOException {
        ArrayList<Picture> pictures = new ArrayList<>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()) {
            pictures.add(readPicture(jsonReader));
        }
        jsonReader.endArray();
        return pictures;
    }

    public static Picture readPicture(JsonReader jsonReader) throws IOException {
        int id = -1;
        String pictName = "";
        String description = "";
        int belongsToNews = -1;
        byte[] pictureData = null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if(name.equals("Id")) {
                id = jsonReader.nextInt();
            } else if (name.equals("Name")) {
                pictName = jsonReader.nextString();
            } else if (name.equals("Description")) {
                description = jsonReader.nextString();
            } else if (name.equals("BelongsToNewsId")) {
                belongsToNews = jsonReader.nextInt();
            } else if (name.equals("PictureData")) {
                String stringData = jsonReader.nextString();
                pictureData = Base64.decode(stringData, Base64.DEFAULT);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return new Picture(id, pictName, description, belongsToNews, pictureData);
    }
}
