package com.newsman.newsman.auxiliary;

import android.content.Context;

import com.newsman.newsman.rest_connection.rest_connectors.CommentConnector;
import com.newsman.newsman.rest_connection.rest_connectors.NewsConnector;
import com.newsman.newsman.rest_connection.rest_connectors.PictureConnector;
import com.newsman.newsman.thread_management.AppExecutors;

public class RequestHelper {
    public static void requestNewsData(Context context, int newsId) {
        getNewsData(context, newsId);
    }

    private static void getNewsData(Context context, int newsId){
        AppExecutors.getInstance().getNetworkIO().execute(
                NewsConnector.loadNewsById(context, newsId)
        );
    }

    private static void getNewsPictures(Context context, int newsId) {
        AppExecutors.getInstance().getNetworkIO().execute(
                PictureConnector.loadPicturesForNews(context, newsId)
        );
    }

    private static void getNewsCommts(Context context, int newsId) {
        AppExecutors.getInstance().getNetworkIO().execute(
                CommentConnector.loadCommentsForNews(context, newsId)
        );
    }
}
