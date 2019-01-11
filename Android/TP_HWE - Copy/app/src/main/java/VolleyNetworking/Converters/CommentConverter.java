package VolleyNetworking.Converters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Auxiliary.DateGetter;
import Entities.Comment;
import Entities.User;

/**
 * Created by Me on 1/10/2019.
 */

public class CommentConverter {

    public static List<Comment> ToArrayOfObjects(JSONArray response) throws JSONException,ParseException {
        ArrayList<Comment> subject = new ArrayList<Comment>();
        DateGetter dg = new DateGetter();

        for(int i = 0; i<response.length(); i++)
        {
            JSONObject obj = response.getJSONObject(i);
            int Id = obj.getInt("Id");
            String Content = obj.getString("Content");
            int BelongsToNewsId = obj.getInt("BelongsToNewsId");
            JSONObject userObj = obj.getJSONObject("CreatedBy");
            int userID = userObj.getInt("Id");
            String username = userObj.getString("Username");
            User CreatedBy = new User(userID,username);
            Date PostDate = dg.getDate(obj.getString("PostDate"));

            Comment nextComment = new Comment(Id,Content,BelongsToNewsId,CreatedBy,PostDate );
            subject.add(nextComment);
        }
        return subject;
    }

    public static Comment ToObject(JSONObject obj) throws JSONException, ParseException {
        DateGetter dg = new DateGetter();
        int Id = obj.getInt("Id");
        String Content = obj.getString("Content");
        int BelongsToNewsId = obj.getInt("BelongsToNewsId");
        JSONObject userObj = obj.getJSONObject("CreatedBy");
        int userID = userObj.getInt("Id");
        String username = userObj.getString("Username");
        User CreatedBy = new User(userID,username);
        Date PostDate = dg.getDate(obj.getString("PostDate"));

        Comment nextComment = new Comment(Id,Content,BelongsToNewsId,CreatedBy,PostDate );
        return nextComment;
    }

    public static JSONObject ToJsonObject(Comment comment) throws JSONException
    {
        JSONObject obj = new JSONObject();

        obj.put("Content", comment.getContent());
        obj.put("BelongsToNewsId", comment.getBelongsToNewsId());
        obj.put("CreatedBy", comment.getCreatedBy().getID());

        return  obj;
    }
}
