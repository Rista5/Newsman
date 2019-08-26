package com.newsman.newsman.auxiliary;

import com.newsman.newsman.server_entities.UserWithPassword;

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
    public static String createNewsRoute() {
        return NEWS_ROUTE + USER_ID;
    }
    public static String updateNewsRoute() { return NEWS_ROUTE + USER_ID; }

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

    public static final int SUBSCRIBED = 1;
    public static final int UNSUBSCRIBED = 0;

    public static String getIpAddress() {
        return IP_ADDRESS;
    }
    // will be used to change ip addresse
    public static void setIpAddress(String adr) {
        IP_ADDRESS = adr;
    }

    public static final String PICTURE_TRASPORT = "file.png";


    //TODO remove later
    // only temorarly
    public static int USER_ID = 3;
    public static UserWithPassword getThisUser() {
        UserWithPassword uswp = new UserWithPassword();
        uswp.setUsername("test");
        uswp.setPassword("test");
        return uswp;
    }
}
