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
}
