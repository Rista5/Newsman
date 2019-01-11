package VolleyNetworking.Providers;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import Entities.Picture;
import VolleyNetworking.QueueSingletonWrap;

/**
 * Created by Me on 1/10/2019.
 */

public class PictureDataProvider {
    private String baseURL;

    public PictureDataProvider(String baseURL) {
        this.baseURL = baseURL + "/api/Picture/";
    }

    public void GetPictureById(int id, Response.Listener<JSONObject> responseListener, Context context)
    {
        String url = baseURL + id;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","Error loading single pic", error);
            }
        });

//        jor.setRetryPolicy(new DefaultRetryPolicy(
//                0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }

    public void GetPictureByNews(int id, Response.Listener<JSONArray> responseListener, Context context)
    {
        String url = baseURL + "FromNews/" + id;

        JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET, url, null,
                responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","Error loading single pic", error);
            }
        });

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }

    public void GetAllPictures(Response.Listener<JSONArray> responseListener, Context context)
    {
        String url = baseURL;

        JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET, url, null,
                responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","Error loading single pic", error);
            }
        });

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }

    public void DeletePicture(int id, Response.Listener<String> responseListener, Context c)
    {
        String url = baseURL +id;
        StringRequest srq = new StringRequest(Request.Method.DELETE, url, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error deleting Picture", error);
            }
        });

        QueueSingletonWrap.getInstance(c).addToRequestQueue(srq);
    }

    public void PostPicture(JSONObject pic, Response.Listener<JSONObject> listener, Context context)
    {
        String url = baseURL;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, pic,
                listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Pic Error", "Error posting selfie", error);
            }
        });

        jor.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }
}
