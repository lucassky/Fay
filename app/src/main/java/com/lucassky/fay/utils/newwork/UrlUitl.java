package com.lucassky.fay.utils.newwork;

/**
 * Created by Administrator on 2015/9/2.
 */
public class UrlUitl {
    public static String ROOT = "https://api.weibo.com/2/";
    //获取关注用户的最新微博
    public static String STATUSES_FRIENDS_TIMELINE = "STATUSES_FRIENDS_TIMELINE";
    //用户的个人信息
    public static String USER_SHOW = "USER_SHOW";
    //微博的评论和转发数量
    public static String WEIBO_REPOSTS_COMMENTS_COUNT = "WEIBO_REPOSTS_COMMENTS_COUNT";
    //微博的评论列表
    public static String WEIBO_COMMENTS = "WEIBO_COMMENTS";
    //微博的转发微博列表
    public static String WEIBO_REPORTS = "WEIBO_REPORTS";
    //搜索某话题下的微博
    public static String SEARCH_TOPICS = "SEARCH_TOPICS";
    //获取当前用户的收藏列表
    public static String FAVORITES= "FAVORITES";
    //获取用户关注的列表
    public static String FRIENDSHIPS = "FRIENDSHIPS";

    public static String getRequestUrl(String key) {
        String urlSuffix = "";
        if (STATUSES_FRIENDS_TIMELINE.equals(key)) {
            urlSuffix = "statuses/friends_timeline.json";
        } else if (USER_SHOW.equals(key)) {
            urlSuffix = "users/show.json";
        } else if (WEIBO_COMMENTS.equals(key)) {
            urlSuffix = "comments/show.json";
        } else if (WEIBO_REPOSTS_COMMENTS_COUNT.equals(key)) {
            urlSuffix = "statuses/count.json";
        } else if (WEIBO_REPORTS.equals(key)) {
            urlSuffix = "statuses/repost_timeline.json";
        } else if (SEARCH_TOPICS.equals(key)) {
            urlSuffix = "search/topics.json";
        }else if (FAVORITES.equals(key)) {
            urlSuffix = "favorites.json";
        }else if(FRIENDSHIPS.equals(key)){
            urlSuffix = "friendships/friends.json";
        }
        return ROOT + urlSuffix;
    }
}
