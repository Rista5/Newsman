package com.newsman.newsman.Auxiliary;

import com.newsman.newsman.ServerEntities.UserWithPassword;

public class Constant {
    private static String IP_ADDRESS = "192.168.1.8";
    public static final String NEWS_BUNDLE_KEY = "news_bundle_key";
    public static final String PICTURE_BUNDLE_KEY = "image_bundle_key";
    public static final int PICTURE_REQUEST_CODE = 12;

    public static final String USER_ROUTE = "/User/";
    public static final String NEWS_ROUTE = "/News/";
    public static final String COMMENT_ROUTE = "/Comment/";
    public static final String PICTURE_ROUTE = "/Picture/";

    public static final String IMAGE_DISPLAY_KEY = "image_display_key";

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RESULT_LOAD_IMAGE = 2;

    public static String getIpAddress() {
        return IP_ADDRESS;
    }
    // will be used to change ip addresse
    public static void setIpAddress(String adr) {
        IP_ADDRESS = adr;
    }

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
