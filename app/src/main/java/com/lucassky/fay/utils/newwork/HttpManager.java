package com.lucassky.fay.utils.newwork;

import android.content.Context;

import com.lucassky.fay.utils.AccessTokenKeeper;
import com.lucassky.fay.utils.Constants;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

/**
 * Created by Administrator on 2015/8/26.
 * HttpManager
 */
public class HttpManager {
    /** HTTP 参数 */
    protected static final String KEY_ACCESS_TOKEN = "access_token";

    public static void getStattuesFriends(Context Context,String tag, long since_id, long max_id, int count, int page,int base_app, int trim_user, int featureType,Callback callback){
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(Context).getToken());
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        params.put("base_app", base_app);
        params.put("trim_user", trim_user);
        params.put("feature", featureType);

        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.STATUSES_FRIENDS_TIMELINE)+"?" + str)
                .build();
        HttpUtil.enqueue(request,callback);
    }

    /**
     * @param context  context
     * @param uid User id
     * @param callback  callback
     */
    public static void getUserInfo(Context context,String tag,long uid,Callback callback){
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN,AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("uid", uid);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.USER_SHOW) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }

    /**
     * @param context
     * @param tag
     * @param id 需要查询的微博ID。
     * @param since_id 若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id 若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
     * @param callback
     */
    public static void getCommtentsByMID(Context context,String tag,long id,long since_id,long max_id,int count,int page,int filter_by_author,Callback callback){
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN,AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("id", id);
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        params.put("filter_by_author", filter_by_author);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.WEIBO_COMMENTS) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }

    /**
     * @param context
     * @param tag
     * @param ids 需要获取数据的微博ID，多个之间用逗号分隔，最多不超过100个。
     * @param callback
     */
    public static void getStatusReportsCommentsCount(Context context,String tag,String ids,Callback callback){
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN,AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("ids", ids);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.WEIBO_REPOSTS_COMMENTS_COUNT) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }
}
