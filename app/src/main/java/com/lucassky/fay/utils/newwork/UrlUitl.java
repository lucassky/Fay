package com.lucassky.fay.utils.newwork;

/**
 * Created by Administrator on 2015/9/2.
 */
public class UrlUitl {
    public static String ROOT = "https://api.weibo.com/2/";
    public static String STATUSES_FRIENDS_TIMELINE = "STATUSES_FRIENDS_TIMELINE";
    public static String USER_SHOW = "USER_SHOW";

    public static String getRequestUrl(String key) {
        String urlSuffix = "";
        if (STATUSES_FRIENDS_TIMELINE.equals(key)) {
            urlSuffix = "statuses/friends_timeline.json";
        } else if (USER_SHOW.equals(key)) {
            urlSuffix = "users/show.json";
        }
        return ROOT + urlSuffix;
    }
}
