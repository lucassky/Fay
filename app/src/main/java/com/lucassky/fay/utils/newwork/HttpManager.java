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
    /**
     * HTTP 参数
     */
    protected static final String KEY_ACCESS_TOKEN = "access_token";

    /**
     * @param Context     上下文
     * @param tag         string 请求的tag
     * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id      int64	若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count       int	单页返回的记录条数，最大不超过100，默认为20。
     * @param page        int	返回结果的页码，默认为1。
     * @param base_app    int	是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param trim_user   int	返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     * @param featureType int	过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param callback    回调
     */
    public static void getStattuesFriends(Context Context, String tag, long since_id, long max_id, int count, int page, int base_app, int trim_user, int featureType, Callback callback) {
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
                .url(UrlUitl.getRequestUrl(UrlUitl.STATUSES_FRIENDS_TIMELINE) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }

    /**
     * @param context  context
     * @param uid      User id
     * @param callback callback
     */
    public static void getUserInfo(Context context, String tag, long uid, Callback callback) {
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("uid", uid);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.USER_SHOW) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }

    /**
     * 获取某条微博评论
     *
     * @param context          上下文
     * @param tag              tag
     * @param id               需要查询的微博ID。
     * @param since_id         若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count            单页返回的记录条数，默认为50。
     * @param page             返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
     * @param callback         回调
     */
    public static void getStatusCommtentsByID(Context context, String tag, long id, long since_id, long max_id, int count, int page, int filter_by_author, Callback callback) {
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());
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
     * 获取某条微博的评论和转发数
     *
     * @param context  上下文
     * @param tag      tag
     * @param ids      需要获取数据的微博ID，多个之间用逗号分隔，最多不超过100个。
     * @param callback 回调
     */
    public static void getStatusReportsCommentsCount(Context context, String tag, String ids, Callback callback) {
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("ids", ids);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.WEIBO_REPOSTS_COMMENTS_COUNT) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }

    /**
     * 获取某条微博的转发微博
     *
     * @param context          上下文
     * @param tag              tag
     * @param id               需要查询的微博ID。
     * @param since_id         若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count            单页返回的记录条数，最大不超过200，默认为20。
     * @param page             返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
     * @param callback         回调
     */
    public static void getStatusReportsByID(Context context, String tag, long id, long since_id, long max_id, int count, int page, int filter_by_author, Callback callback) {
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("id", id);
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        params.put("filter_by_author", filter_by_author);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.WEIBO_REPORTS) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }


    /**
     * 查询某个话题下的微博
     *
     * @param context  上下文
     * @param tag      tag
     * @param q        string	搜索的话题关键字，必须进行URLencode，utf-8编码。
     * @param count    int	单页返回的记录条数，默认为10，最大为50。
     * @param page     int	返回结果的页码，默认为1。
     * @param callback 回调
     */
    public static void getStatusesByTopics(Context context, String tag, String q, int count, int page, Callback callback) {
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("q", q);
        params.put("count", count);
        params.put("page", page);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.SEARCH_TOPICS) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }

    /**
     * 获取当前登录用户的收藏列表
     *
     * @param context  上下文
     * @param tag      tag
     * @param count    int	单页返回的记录条数，默认为50。
     * @param page     int	返回结果的页码，默认为1。
     * @param callback 回调
     */
    public static void getFavorites(Context context, String tag, int count, int page, Callback callback) {
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put(KEY_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());
        params.put("count", count);
        params.put("page", page);
        String str = params.encodeUrl();
        Request request = new Request.Builder()
                .tag(tag)
                .url(UrlUitl.getRequestUrl(UrlUitl.FAVORITES) + "?" + str)
                .build();
        HttpUtil.enqueue(request, callback);
    }
}
