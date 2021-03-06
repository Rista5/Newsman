package com.newsman.newsman.auxiliary;

import com.newsman.newsman.model.db_entities.UserWithPassword;

public class Constant {
    private static String IP_ADDRESS = "192.168.1.7";
    public static final String NEWS_BUNDLE_KEY = "news_bundle_key";
    public static final String PICTURE_BUNDLE_KEY = "image_bundle_key";
    public static final int PICTURE_REQUEST_CODE = 12;
    public static final int PICTURE_LOADER_ID = 20;
    public static final int PICTURE_TRANSPORT_LOADER = 21;

    public static final String USER_ROUTE = "/User/";
    public static final String NEWS_ROUTE = "/News/";
    public static final String COMMENT_ROUTE = "/Comment/";
    public static final String PICTURE_ROUTE = "/Picture/";
    public static final String RAW_PICTURE_ROUTE = "/Picture/Raw/";

    public static final String IMAGE_DISPLAY_KEY = "image_display_key";

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;
    public static final int PICTURE_FRAGMENT_REQ_LOAD = 3;
    public static final int PICTURE_FRAGMENT_REQ_CAPTURE = 4;
    public static final int NEWS_FRAGMENT_REQ_LOAD = 5;
    public static final int NEWS_FRAGMENT_REQ_CAPTURE = 6;

    public static final int INVALID_PICTURE_ID = 0;
    public static final int INVALID_NEWS_ID = 0;
    public static final int INVALID_COMMENT_ID = 0;
    public static final int INVALID_USER_ID = 0;

    public static final int SUBSCRIBED = 1;
    public static final int UNSUBSCRIBED = 0;

    public static final int PICRURE_NOT_ON_DISC = 0;
    public static final int PICTURE_ON_DISC = 1;

    public static String getIpAddress() {
        return IP_ADDRESS;
    }
    // will be used to change ip addresse
    public static void setIpAddress(String adr) {
        IP_ADDRESS = adr;
    }

    public static String getBaseUrl(){
        String url = "http://" + Constant.getIpAddress() + ":52752/api/";
        return  url;
    }

    public static String getRawPictureRoute(int picId, int newsId){
        String route = Constant.RAW_PICTURE_ROUTE + "?picId="+picId+"&newsId="+newsId;
        return route;
    }
    public static final String PICTURE_TRASPORT = "file.png";
}
