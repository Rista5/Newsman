package VolleyNetworking.Providers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import VolleyNetworking.QueueSingletonWrap;

/**
 * Created by Me on 1/10/2019.
 */

public class AudioDataProvider {

    private String baseURL;

    public AudioDataProvider(String baseURL) {
        this.baseURL = baseURL + "/api/Audio/";
    }


    public void GetAudioById(int id, Response.Listener<JSONObject> responseListener, Context context)
    {
        String url = baseURL + id;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","Error loading single pic", error);
            }
        });

        QueueSingletonWrap.getInstance(context).addToRequestQueue(jor);
    }

    public void GetAudioByNews(int id, Response.Listener<JSONArray> responseListener, Context context)
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

    public void GetAllAudio(Response.Listener<JSONArray> responseListener, Context context)
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

    public void DeleteAudio(int id, Response.Listener<String> responseListener, Context c)
    {
        String url = baseURL +id;
        StringRequest srq = new StringRequest(Request.Method.DELETE, url, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error deleting Audio", error);
            }
        });

        QueueSingletonWrap.getInstance(c).addToRequestQueue(srq);
    }
}
