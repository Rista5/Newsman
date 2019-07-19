package com.newsman.newsman.ServerEntities;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JsonSerializer {

    public static News readNews(JsonReader jsonReader)throws IOException {
        int id = 0;
        String title = "";
        String content="";
        ArrayList<Comment> comments = new ArrayList<>();
        String lastModified = "";
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String name = jsonReader.nextName();
            if(name.equals("Id")){
                id = jsonReader.nextInt();
            }else if(name.equals("Title")){
                title = jsonReader.nextString();
            }else if(name.equals("Content")){
                content = jsonReader.nextString();
            }else if(name.equals("Comments")){
                comments = readComments(jsonReader);
            }else if(name.equals("LasModified")){
                lastModified = jsonReader.nextString();
            }else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return new News(id,title,content,comments,lastModified);
    }

    public static ArrayList<Comment> readComments(JsonReader jsonReader)throws IOException {
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
}
