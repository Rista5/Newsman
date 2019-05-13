package com.newsman.newsman.Providers;

public class Constant {
    private static String IP_ADDRESS = "192.168.1.7";
    public static final String NEWS_BUNDLE_KEY = "news";

    public static String getIpAddress() {
        return IP_ADDRESS;
    }

    public static void setIpAddress(String adr) {
        IP_ADDRESS = adr;
    }
}
