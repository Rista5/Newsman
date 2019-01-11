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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Entities.News;
import VolleyNetworking.Converters.NewsConverter;
import VolleyNetworking.QueueSingletonWrap;

/**
 * Created by Me on 1/10/2019.
 */

public class NewsDataProvider
{
    String baseURL;

    private NewsDataProvider() {}

    public NewsDataProvider(String passedBaseURL)
    {
        baseURL = passedBaseURL + "/api/News";
    }

    public void GetNewsById(int id, Response.Listener<JSONObject> specificResponse, Context context)
    {
        String url = baseURL + "/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                specificResponse, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error loading specific news", error);
            }
        });
        QueueSingletonWrap.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetAllNews(Response.Listener<JSONArray> specificResponse, Context context)
    {
        String url = baseURL + "/";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                specificResponse, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AllError", "Error loading all news",error);
            }
        });
        QueueSingletonWrap.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void DeleteNews(int id, Response.Listener<String> responseListener, Context c)
    {
        String url = baseURL+ "DeleteNews/" + id;
        StringRequest srq = new StringRequest(Request.Method.DELETE, url, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error deleting News", error);
            }
        });

        QueueSingletonWrap.getInstance(c).addToRequestQueue(srq);
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

    public void Put(News news, Response.Listener<JSONObject> stringListener, Context context) throws JSONException
    {
        String url = baseURL + "/" + news.getId();

        JSONObject obj = NewsConverter.ToJSonObject(news);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.PUT, url, obj, stringListener, new Response.ErrorListener() {
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
