package VolleyNetworking.Converters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Auxiliary.DateGetter;
import Entities.Comment;
import Entities.News;
import Entities.Picture;
import Entities.User;

/**
 * Created by Me on 1/10/2019.
 */

public class NewsConverter {
    public static News ConvertToObject(JSONObject response) throws JSONException,ParseException
    {
        News subject = new News();
        DateGetter dg = new DateGetter();

        subject.setId(response.getInt("Id"));
        subject.setTitle(response.getString("Title"));
        subject.setContent(response.getString("Content"));
        Date lastModified = dg.getDate(response.getString("LastModified"));
        subject.setLastModified(lastModified);

        JSONArray jSonComments = response.getJSONArray("Comments");
        List<Comment> comments = CommentConverter.ToArrayOfObjects(jSonComments);
        subject.setComments(comments);

//        JSONArray jSonPictures = response.getJSONArray("Pictures");
//        List<Picture> pictures = PictureConverter.ToArrayOfObjects(jSonPictures);
//        subject.setPictures(pictures);

        return  subject;
    }

    public static JSONObject ToJSonObject(News news)
    {
        JSONObject obj = new JSONObject();



        return obj;
    }
}
