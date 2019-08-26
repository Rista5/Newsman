package com.newsman.newsman.message_queue;

import android.graphics.Bitmap;
import android.util.Base64;

import com.newsman.newsman.auxiliary.DateGetter;
import com.newsman.newsman.auxiliary.PictureConverter;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
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
        User user = parseUser(json.getJSONObject("CreatedBy"));
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        String postDate = json.getString("PostDate");
        return new Comment(commentId, commentContent, user.getId(), belongsToNews, postDate);
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
        JSONArray commentsJson = json.getJSONArray("Comments");
        List<Comment> comments = new ArrayList<>(commentsJson.length());
        for(int i=0; i<commentsJson.length(); i++) {
            comments.add(parseComment(commentsJson.getJSONObject(i)));
        }
        JSONArray picturesArray = json.getJSONArray("Pictures");
        List<Picture> pictures = new ArrayList<>(picturesArray.length());
        for(int i=0; i<picturesArray.length(); i++) {
            pictures.add(parsePicture(picturesArray.getJSONObject(i)));
        }
        String lastModified = json.getString("LasModified");
        Picture back = parsePicture(json.getJSONObject("BackgroundPicture"));
        return new News(id,title,content,comments, DateGetter.parseDate(lastModified),
                pictures, back.getId());
    }

}
