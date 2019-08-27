package com.newsman.newsman.message_queue.update_objects;

import android.graphics.Bitmap;
import android.util.Base64;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.DateAux;
import com.newsman.newsman.auxiliary.PictureConverter;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;
import com.newsman.newsman.server_entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static User parseUser(JSONObject json) throws JSONException {
        return new User(
                Integer.parseInt(json.getString("Id")),
                json.getString("Username")
        );
    }

    public static Comment parseComment(JSONObject json) throws JSONException {
        int commentId = json.getInt("Id");
        JSONObject userJson = json.getJSONObject("CreatedBy");
        User user = parseUser(userJson);
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        String postDate = json.getString("PostDate");
        return new Comment(commentId, commentContent, user.getId(), belongsToNews, postDate,
                user.getUsername());
    }


    public static Picture parsePicture(JSONObject json) throws JSONException {
        int pictureId = json.getInt("Id");
        String name = json.getString("Name");
        String description = json.getString("Description");
        int newsId = json.getInt("BelongsToNewsId");
        byte[] data = Base64.decode(json.getString("PictureData"), Base64.DEFAULT);
        Bitmap bmp = null;
        try{
            bmp = PictureConverter.getBitmap(data);
        }catch (NullPointerException e ){
            e.printStackTrace();
        }
        return new Picture(pictureId, name, description, newsId, bmp);
    }

    public static News parseNews(JSONObject json) throws JSONException {
        int id = json.getInt("Id");
        String title = json.getString("Title");
        String content = json.getString("Content");
        String lastModified = json.getString("LasModified");
        User user = parseUser(json.getJSONObject("LastModifiedUser"));
        Picture back = parsePicture(json.getJSONObject("BackgroundPicture"));
        JSONArray picturesArray = json.getJSONArray("Pictures");
        List<Picture> pictures = new ArrayList<>(picturesArray.length());
        for(int i=0; i<picturesArray.length(); i++) {
            pictures.add(parsePicture(picturesArray.getJSONObject(i)));
        }
        JSONArray commentsJson = json.getJSONArray("Comments");
        List<Comment> comments = new ArrayList<>(commentsJson.length());
        for(int i=0; i<commentsJson.length(); i++) {
            comments.add(parseComment(commentsJson.getJSONObject(i)));
        }
        return new News(id,title,content,comments, DateAux.parseDate(lastModified),
                pictures, back.getId(), user.getId(), user.getUsername());
    }

    public static SimpleNews parseSimpleNews(JSONObject json) throws JSONException {
        int id = json.getInt("Id");
        String title = json.getString("Title");
        String content = json.getString("Content");
        String lastModified = json.getString("LasModified");
        User user = JSONParser.parseUser(json.getJSONObject("LastModifiedUser"));
        Bitmap background = null;
        int backId = Constant.INVALID_PICTURE_ID;
        JSONObject backPic = json.getJSONObject("BackgroundPicture");
        if(backPic != null){
            Picture picture= JSONParser.parsePicture(json.getJSONObject("BackgroundPicture"));
            backId = picture.getId();
            background = picture.getPictureData();
        }
        return new SimpleNews(id, title, content, DateAux.parseDate(lastModified),
                background, backId, user.getId(), user.getUsername());
    }

}
