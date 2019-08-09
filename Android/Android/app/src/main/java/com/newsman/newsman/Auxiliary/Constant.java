package com.newsman.newsman.Auxiliary;

import com.newsman.newsman.ServerEntities.UserWithPassword;

public class Constant {
    private static String IP_ADDRESS = "192.168.1.7";
    public static final String NEWS_BUNDLE_KEY = "news";
    public static final String NEWS_EXTRA_ID_KEY = "news_extra_id_key";

    public static String USER_ROUTE = "/User/";
    public static String NEWS_ROUTE = "/News/";
    public static String COMMENT_ROUTE = "/Comment/";
    public static String PICTURE_ROUTE = "/Picture/";

    public static String IMAGE_DISPLAY_KEY = "image_display_key";

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
