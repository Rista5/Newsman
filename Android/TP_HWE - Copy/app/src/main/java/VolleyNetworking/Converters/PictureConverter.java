package VolleyNetworking.Converters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import Entities.Picture;

public class PictureConverter {
    public static Picture ToObject(JSONObject obj) throws JSONException
    {
        Picture pic = new Picture();

        pic.setBelongsToNewsId(obj.getInt("BelongsToNewsId"));
        pic.setDescription(obj.getString("Description"));
        pic.setId(obj.getInt("Id"));
        pic.setName(obj.getString("Name"));
        byte[] bytes = Base64.decode(obj.getString("PictureData").getBytes(),Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        pic.setPictureData(bmp);

        return pic;
    }

    public static List<Picture> ToArrayOfObjects(JSONArray array) throws JSONException
    {
        ArrayList<Picture> PictureList = new ArrayList<Picture>();
        for(int i = 0; i<array.length(); i++)
        {
            JSONObject obj = array.getJSONObject(i);
            Picture pic = ToObject(obj);
            PictureList.add(pic);
        }

        return PictureList;
    }

    public static JSONObject ToJsonObject(Picture pic) throws JSONException
    {
        JSONObject obj = new JSONObject();
        obj.put("Id", pic.getId());
        obj.put("Name", pic.getName());
        obj.put("Description", pic.getDescription());
        obj.put("BelongsToNewsId", pic.getBelongsToNewsId());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        pic.getPictureData().compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        obj.put("PictureData",byteArray);

        return obj;
    }
}
