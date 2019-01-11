package VolleyNetworking.Providers;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Entities.Comment;
import VolleyNetworking.QueueSingletonWrap;

/**
 * Created by Me on 1/10/2019.
 */

public class CommentDataProvider {

    String baseURL;

    private CommentDataProvider() {}
    public CommentDataProvider(String passedUrl)
    {
        baseURL = passedUrl + "/api/Comment";
    }

    public void GetAllComments(Response.Listener<JSONArray> specificResponse, Context context) {
        String url = baseURL;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
        specificResponse,new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error loading all comments", error);
            }
        });

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void GetCommentById(int id, Response.Listener<JSONObject> specificResponse, Context context) {
        String url = baseURL + "/" +id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                specificResponse, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error","Error loading comment", error);
            }
        });

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetCommentByNews(int id, Response.Listener<JSONArray> specificResponse, Context context) {
        String url = baseURL + "/FromNews/" + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                specificResponse, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error","Error loading comment", error);
            }
        });

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void DeleteComment(int id, Response.Listener<String> specificResponse, Context contexrt) {
        String url = baseURL + "/" +id;
        StringRequest srq = new StringRequest(Request.Method.DELETE, url, specificResponse, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error deleting comment", error);
            }
        });

        QueueSingletonWrap.getInstance(contexrt).addToRequestQueue(srq);
    }

    public void Post(JSONObject com, Response.Listener<JSONObject> specificResponse, Context context)
    {
        String url = baseURL;
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, com, specificResponse, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err", "err2", error);
            }
        });

        jor.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }

    public void Put(final int id,final String content, Response.Listener<JSONObject> stringListener, Context context) throws JSONException
    {
        String url = baseURL + "/" + id;

        JSONObject com = new JSONObject();
        com.put("Content", content);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.PUT, url, com, stringListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err", "err2", error);
            }
        });

        jor.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        QueueSingletonWrap.getInstance(context).getRequestQueue().getCache().clear();
        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }
}
