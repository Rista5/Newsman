package VolleyNetworking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Me on 1/10/2019.
 */

public class QueueSingletonWrap {
    private static QueueSingletonWrap mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private QueueSingletonWrap(Context context)
    {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return  mRequestQueue;
    }

    public static  synchronized QueueSingletonWrap getInstance(Context context){
        if(mInstance == null)
        {
            mInstance = new QueueSingletonWrap(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
