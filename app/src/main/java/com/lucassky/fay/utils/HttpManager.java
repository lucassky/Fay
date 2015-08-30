package com.lucassky.fay.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.lucassky.fay.model.StatusesResult;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2015/8/26.
 * HttpManager
 */
public class HttpManager {
    /** HTTP 参数 */
    protected static final String KEY_ACCESS_TOKEN = "access_token";

    public static void getStattuesFriends(Context Context,long since_id, long max_id, int count, int page,int base_app, int trim_user, int featureType,Callback callback){
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN,AccessTokenKeeper.readAccessToken(Context).getToken());
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        params.put("base_app", base_app);
        params.put("trim_user", trim_user);
        params.put("feature", featureType);

        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .url("https://api.weibo.com/2/statuses/friends_timeline.json"+"?" + str)
                .build();
        HttpUtil.enqueue(request,callback);

    }
}
